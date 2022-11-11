package org.uninstal.client.fxml;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.uninstal.client.Client;
import org.uninstal.client.connection.Connection;
import org.uninstal.client.connection.impl.PacketCallAuthorization;

public class AuthScene {

  private static AuthScene instance;
  private long lock = System.currentTimeMillis();
  
  public AnchorPane PANE;
  public Button LOGIN_BUTTON;
  public PasswordField PASSWORD_AREA;
  public TextField LOGIN_AREA;
  public Label ERROR_AREA;

  @FXML
  void initialize() {
    instance = this;
    
    LOGIN_BUTTON.setOnAction(event -> {
      if (!Client.isConnected()) {
        showError("Отсутствует подключение к серверу авторизации...");
        lock(5000L);
        return;
      }
      
      Connection connection = Client.getConnection();
      connection.sendPacket(new PacketCallAuthorization(connection,
        LOGIN_AREA.getText(), PASSWORD_AREA.getText()));
    });
  }

  public static AuthScene getInstance() {
    return instance;
  }

  public synchronized void showError(String text) {
    if (lock < System.currentTimeMillis()) {
      Platform.runLater(() -> {
        ERROR_AREA.setTextFill(Color.RED);
        ERROR_AREA.setText(text);
        ERROR_AREA.setVisible(true);
      });
    }
  }

  public synchronized void showStatus(String text) {
    if (lock < System.currentTimeMillis()) {
      Platform.runLater(() -> {
        ERROR_AREA.setTextFill(Color.WHITE);
        ERROR_AREA.setText(text);
        ERROR_AREA.setVisible(true);
      });
    }
  }
  
  public synchronized void lock(long millis) {
    this.lock = System.currentTimeMillis() + millis;
  }
  
  public synchronized void hideText() {
    Platform.runLater(() -> 
      ERROR_AREA.setVisible(false));
  }
}