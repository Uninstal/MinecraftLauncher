package org.uninstal.client.connection.download;

import org.uninstal.client.util.Paths;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DownloadProcess {
  
  private final long start = System.currentTimeMillis();
  private final int processId;
  private final int totalFiles;
  
  private int downloadedFiles;
  private int skippedFiles;
  private int failedFiles;
  private String currentFile;
  private int currentFilePersent;
  
  public DownloadProcess(int processId, int totalFiles) {
    this.processId = processId;
    this.totalFiles = totalFiles;
    this.downloadedFiles = 0;
    this.skippedFiles = 0;
    this.failedFiles = 0;
    this.currentFile = "null";
    this.currentFilePersent = 0;
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
  
  public synchronized void buildFile(DataInputStream input, String folder, String fileName) {
    String path = Paths.getHomeLocation() + folder;
    new File(path).mkdirs();
    File file = new File(path, fileName);
    
    try (FileOutputStream fileOutput = new FileOutputStream(file)) {
      if (!file.exists()) {
        currentFile = fileName;
        
        int lengthTemp = input.readInt();
        byte[] array = new byte[8096];
        while (lengthTemp > 0) {
          int read = input.read(array);
          fileOutput.write(array, 0, read);
          fileOutput.flush();
          lengthTemp -= read;
          currentFilePersent = 100 - (int) ((lengthTemp * 100) / file.length());
        }
        
        System.out.println("Создан файл " + folder + "/" + fileName);
        downloadedFiles++;
        
        if (file.getName().equalsIgnoreCase("game.rar")) {
          try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(file.toPath()))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
              File target = new File(path, entry.getName());
              
              if (!target.exists()) {
                if (entry.isDirectory())
                  target.mkdirs();
                else {
                  Path targetPath = target.toPath();
                  Files.createDirectory(targetPath.getParent());
                  Files.copy(zis, targetPath);
                }
              }
            }
            System.out.println("Архив " + file.getName() + " распакован");
          } catch(Exception e) {
            System.out.println("Ошибка при распаковке архива:");
            e.printStackTrace();
          }
          file.delete();
        }
        
      } else skippedFiles++;
      
    } catch(Exception e) {
      e.printStackTrace();
      failedFiles++;
    }
  }

  public String getCurrentFile() {
    return currentFile;
  }

  public int getCurrentFilePersent() {
    return currentFilePersent;
  }

  public void down() {
    double time = (System.currentTimeMillis() - start) / 1000d;
    System.out.println("Скачано " + totalFiles + " файлов " +
      "(" + downloadedFiles + " успешно, " + skippedFiles + " пропущено, " + failedFiles + " неудачно) за " + time + " с.");
  }
}