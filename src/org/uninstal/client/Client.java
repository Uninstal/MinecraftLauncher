package org.uninstal.client;

import org.uninstal.client.connection.Connection;
import org.uninstal.client.fxml.FxmlController;

public class Client {
  
  private static Connection connection;
  private static boolean connected;
  private static Account account;
  
  public static void main() {
    connection = new Connection("mc.ndaz.ru", 21111);
    connection.tryConnect(connected -> {
      if(!connected) {
        FxmlController.getProgressBarController().setText(
          "Попытка подключения к серверу... (" + connection.getAttempts() + ")");
      } else {
        FxmlController.getProgressBarController().disable();
        connection.runThread();
      }
    });
  }

  public static Connection getConnection() {
    return connection;
  }

  public static boolean isConnected() {
    return connection != null && connection.isConnected();
  }

  public static Account getAccount() {
    return account;
  }

  public static void setAccount(Account account) {
    Client.account = account;
  }

  public static boolean isAuthenticated() {
    return account != null;
  }
}