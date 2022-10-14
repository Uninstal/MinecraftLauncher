package org.uninstal.client.util;

import org.uninstal.client.Client;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;
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
      return Client.class.getResource(path).toURI().toURL();
    } catch(Exception e) {
      return null;
    }
  }
  
  public static Image getImage(String path) {
    return getIcon(path).getImage();
  }

  public static ImageIcon getIcon(String path) {
    return new ImageIcon(Objects.requireNonNull(Client.class.getResource(path)));
  }
}