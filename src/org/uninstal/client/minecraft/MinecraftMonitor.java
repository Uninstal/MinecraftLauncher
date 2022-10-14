package org.uninstal.client.minecraft;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class MinecraftMonitor {
  
  private final BufferedReader logger;
  private final BufferedReader error;
  
  public MinecraftMonitor(InputStream logger, InputStream error) {
    this.logger = new BufferedReader(new InputStreamReader(logger));
    this.error = new BufferedReader(new InputStreamReader(error));
    infinity("MinecraftLogger",this.logger, System.out::println);
    infinity("MinecraftReporter", this.error, System.out::println);
  }

  public BufferedReader getErrorStream() {
    return error;
  }

  public BufferedReader getLoggerStream() {
    return logger;
  }
  
  private void infinity(String id, BufferedReader reader, Consumer<String> action) {
    try {
      String line;
      while ((line = reader.readLine()) != null)
        action.accept(line);
      System.out.println("Поток " + id + " завершил работу");
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}