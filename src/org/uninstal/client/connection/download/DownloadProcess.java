package org.uninstal.client.connection.download;

import org.uninstal.client.util.Paths;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;

public class DownloadProcess {
  
  private final long start = System.currentTimeMillis();
  private final int processId;
  private final int total;
  private final boolean replace;
  
  private int downloaded;
  private int skipped;
  private int failed;
  private String currentFile;
  
  public DownloadProcess(int processId, int total, boolean replace) {
    this.replace = replace;
    this.processId = processId;
    this.total = total;
    this.downloaded = 0;
    this.skipped = 0;
    this.failed = 0;
    this.currentFile = "none";
  }

  public int getProcessId() {
    return processId;
  }

  public int getDownloaded() {
    return downloaded;
  }

  public int getSkipped() {
    return skipped;
  }

  public int getFailed() {
    return failed;
  }

  public int getTotal() {
    return total;
  }

  public void buildFile(DataInputStream input, String folder, String fileName) throws IOException {
    
    try {
      File file = Paths.getResourceFileWithDirs(folder, fileName);
      int length = input.readInt();

      boolean exists = file.exists();
      if (replace && (!exists || file.delete()) || !exists) {
        file.createNewFile();
        try (FileOutputStream fileOutput = new FileOutputStream(file)) {
          currentFile = fileName;

          byte[] array = new byte[length];
          int read = 0;
          while (read < length)
            read += input.read(array, read, length - read);
          fileOutput.write(array);
          fileOutput.flush();
          downloaded++;

        } catch (SocketException ignored) {
        } catch(Exception e) {
          e.printStackTrace();
          failed++;
        }

      } else {
        input.skipBytes(length);
        skipped++;
      }
      onBuildFile();
    } catch(SecurityException e) {
      System.out.println("Cannot get access for manipulation with files");
    }
  }

  public String getCurrentFile() {
    return currentFile;
  }
  
  public int getReaded() {
    return downloaded + failed + skipped;
  }
  
  public int getRemain() {
    return total - getReaded();
  }
  
  public void down() {
    double time = (System.currentTimeMillis() - start) / 1000d;
    System.out.println("Installed " + total + " files (" 
      + downloaded + " success, " 
      + skipped + " skip, " 
      + failed + " fail" +
      ") with " + time + " sec.");
    onDownloadEnd();
  }
  
  protected void onDownloadEnd() {}
  protected void onBuildFile() {}
}