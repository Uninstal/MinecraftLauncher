package org.uninstal.client.minecraft;

import java.util.Arrays;

public class MinecraftProcessBuilder {
  
  private final static String separator = "#";
  private final ProcessBuilder builder = new ProcessBuilder();
  private final StringBuilder argsBuilder = new StringBuilder();

  /**
   * @param arg аргумент запуска, который отделяется специальным символом от других.
   * @return метод возвращает данный класс.
   */
  public MinecraftProcessBuilder append(String arg) {
    argsBuilder.append(argsBuilder.length() == 0 ? arg : separator + arg);
    return this;
  }

  /**
   * Необходимо выполнять в отдельном потоке, чтобы избежать возможных зависаний.
   * Входящие потоки теперь обрабатываются в классе MinecraftMonitor.
   */
  public Process start() {
    String[] args = argsBuilder.toString().split(separator);
    try {
      builder.command(args);
      System.out.println("Аргументы запуска:");
      System.out.println(Arrays.toString(args));
      return builder.start();
    } catch (Exception e) {
      System.out.println("Ошибка при запуске майнкрафта:");
      e.printStackTrace();
      return null;
    }
  }
}