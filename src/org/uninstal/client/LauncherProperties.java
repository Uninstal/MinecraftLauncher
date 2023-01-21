package org.uninstal.client;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

public class LauncherProperties {
  
  private static final Properties properties = new Properties();
  
  protected static void load(File file) throws IOException {
    if (file.exists()) properties.load(Files.newInputStream(file.toPath()));
  }
  
  protected static void save(File file) throws IOException {
    properties.store(Files.newOutputStream(file.toPath()), null);
  }
  
  public static boolean hasAuthorizationData() {
    return properties.containsKey("login") && properties.containsKey("password");
  }
  
  public static String getLogin() {
    return properties.getProperty("login");
  }
  
  public static String getPassword() {
    return properties.getProperty("password");
  }
  
  public static void setLogin(String login) {
    properties.setProperty("login", login);
  }
  
  public static void setPassword(String password) {
    properties.setProperty("password", password);
  }
}