package org.uninstal.client;

import org.uninstal.client.connection.Connection;
import org.uninstal.client.fxml.AuthScene;
import org.uninstal.client.fxml.PlayScene;
import org.uninstal.client.util.Strings;

public class Client {
  
  private static String nickname;
  private static Connection connection;
  private static boolean connected;
  
  public static void main() {
    connection = new Connection("mc.ndaz.ru", 21111);
    // connection = new Connection("127.0.0.1", 25565);
    connection.tryConnect(connected -> {
      if (connected) {
        System.out.println("Successfully connected with " + connection.getAttempts() + " attempts");
        AuthScene.getInstance().hideText();
        connection.runThread();
      } else {
        AuthScene.getInstance().showStatus("Подключение" 
          + Strings.repeat(".", (connection.getAttempts() + 1) % 3 + 1));
      }
    });
  }

  public static void setNickname(String nickname) {
    Client.nickname = nickname;
    PlayScene.getInstance().setNickname(nickname);
  }

  public static String getNickname() {
    return nickname;
  }

  public static Connection getConnection() {
    return connection;
  }

  public static boolean isConnected() {
    return connection != null && connection.isConnected();
  }
}