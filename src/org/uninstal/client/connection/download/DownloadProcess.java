package org.uninstal.client.connection.download;

import org.uninstal.client.util.Paths;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DownloadProcess {
  
  private final long start = System.currentTimeMillis();
  private final int processId;
  private final int totalFiles;
  
  private int downloadedFiles;
  private int skippedFiles;
  private int failedFiles;
  private String currentFile;
  
  public DownloadProcess(int processId, int totalFiles) {
    this.processId = processId;
    this.totalFiles = totalFiles;
    this.downloadedFiles = 0;
    this.skippedFiles = 0;
    this.failedFiles = 0;
    this.currentFile = "null";
  }

  public int getProcessId() {
    return processId;
  }

  public int getDownloadedFiles() {
    return downloadedFiles;
  }
  
  public int getSkippedFiles() {
    return skippedFiles;
  }

  public int getFailedFiles() {
    return failedFiles;
  }

  public int getTotalFiles() {
    return totalFiles;
  }
  
  public void buildFile(DataInputStream input, String folder, String fileName) throws IOException {
    
    String path = Paths.getHomeLocation() + folder;
    new File(path).mkdirs();
    File file = new File(path, fileName);
    int length = input.readInt();
    
    if (!file.exists()) {
      try (FileOutputStream fileOutput = new FileOutputStream(file)) {
        currentFile = fileName;
        
        byte[] array = new byte[length];
        int read = 0;
        while (read < length) {
          read += input.read(array, read, length - read);
        }
        fileOutput.write(array);
        fileOutput.flush();

        //System.out.println("Создан файл " + folder + "/" + fileName);
        downloadedFiles++;

      } catch(Exception e) {
        e.printStackTrace();
        failedFiles++;
      }

    } else {
      input.skipBytes(length);
      skippedFiles++;
    }
  }

  public String getCurrentFile() {
    return currentFile;
  }
  
  public int getReadedFilesCount() {
    return totalFiles - downloadedFiles - failedFiles - skippedFiles;
  }

  public void down() {
    double time = (System.currentTimeMillis() - start) / 1000d;
    System.out.println("Скачано " + totalFiles + " файлов " +
      "(" + downloadedFiles + " успешно, " + skippedFiles + " пропущено, " + failedFiles + " неудачно) за " + time + " с.");
  }
}