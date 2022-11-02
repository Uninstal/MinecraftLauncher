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

  //C:/Program Files/Java Projects/saves 4/Launcher/out/production/Client
  private static String HOME;
  
  static {
    try {
      HOME = Client.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
      HOME = HOME.substring(0, HOME.lastIndexOf("/"));
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  public static String getHomeLocation() {
    return HOME;
  }
  
  public static URL getUrl(String path) {
    try {
      return Objects.requireNonNull(Client.class.getResource(path)).toURI().toURL();
    } catch(Exception e) {
      return null;
    }
  }

  public static List<File> getFiles(String folderPath) {
    return getFiles(new File(folderPath));
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
    }
    return files;
  }
  
  public static Image getImage(String path) {
    return getIcon(path).getImage();
  }

  public static ImageIcon getIcon(String path) {
    return new ImageIcon(Objects.requireNonNull(Client.class.getResource(path)));
  }
}