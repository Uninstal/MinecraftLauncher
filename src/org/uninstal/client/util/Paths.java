package org.uninstal.client.util;

import org.uninstal.client.Client;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Paths {

  private  static String HOME;

  static {
    try {
      HOME = Client.class.getProtectionDomain().getCodeSource().getLocation().getPath();
      HOME = HOME.substring(0, HOME.lastIndexOf("/"));
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  public static String getDefaultLocation() {
    return HOME;
  }

  /**
   * Данный метод возвращает файл, находящийся в папке jar-файла.
   * @param fileName название файла вместе с его расширением.
   * @return возвращает объект файла, но не создает сам файл.
   */
  public static File getResourceFile(String fileName) {
    return new File(HOME, fileName);
  }

  /**
   * Данный метод возвращает файл, находящийся в папке jar-файл + папка(и) folder
   * @param folder папка файла (разделитель - '/').
   * @param fileName название файла вместе с его расширением.
   * @return возвращает объект файла, но не создает сам файл.
   */
  public static File getResourceFile(String folder, String fileName) {
    return new File(HOME + "/" + folder, fileName);
  }

  /**
   * Данный метод возвращает файл, потенциально находящийся в папке jar-файл + папка(и) folder
   * и создает их, если они отсутствуют.
   * @param folder папка файла (разделитель - '/').
   * @param fileName название файла вместе с его расширением.
   * @return возвращает объект файла, но не создает сам файл.
   */
  public static File getResourceFileWithDirs(String folder, String fileName) {
    File file = getResourceFile(folder, fileName);
    file.getParentFile().mkdirs();
    return file;
  }

  public static URL getUrl(String path) {
    try {
      URL resource = Client.class.getResource(path);
      return resource == null ? null : resource.toURI().toURL();
    } catch(Exception e) {
      return null;
    }
  }

  public static List<File> getFiles(String folder) {
    return getFiles(new File(folder));
  }

  private static List<File> getFiles(File folder) {
    List<File> files = new ArrayList<>();
    File[] folderFiles = folder != null ? folder.listFiles() : null;
    if (folderFiles != null) {
      for (File file : folderFiles) {
        if (file.isDirectory()) {
          files.addAll(getFiles(file));
        } else files.add(file);
      }
    } else System.out.println("Folder or files in folder is empty");
    return files;
  }

  public static Image getImage(String path) {
    return getIcon(path).getImage();
  }

  public static ImageIcon getIcon(String path) {
    return new ImageIcon(Objects.requireNonNull(Client.class.getResource(path)));
  }
}