package org.uninstal.client.minecraft;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class MinecraftMonitor {
  
  public MinecraftMonitor(InputStream logger, InputStream error) {
    infinityReader("MinecraftLogger", new BufferedReader(new InputStreamReader(logger)), System.out::println);
    infinityReader("MinecraftReporter", new BufferedReader(new InputStreamReader(error)), System.out::println);
  }
  
  private void infinityReader(String id, BufferedReader reader, Consumer<String> action) {
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