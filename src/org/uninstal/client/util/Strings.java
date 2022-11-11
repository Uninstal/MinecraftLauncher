package org.uninstal.client.util;

public class Strings {
  
  public static String repeat(String string, int amount) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < amount; i++)
      builder.append(string);
    return builder.toString();
  }
}