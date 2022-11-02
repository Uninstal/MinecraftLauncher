package org.uninstal.client.fxml;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.uninstal.client.Client;
import org.uninstal.client.connection.Connection;
import org.uninstal.client.connection.impl.PacketCallAuthorization;

public class AuthScene {
  
  private static AuthScene instance;
  
  @FXML public Button LOGIN_BUTTON;
  @FXML public PasswordField PASSWORD_AREA;
  @FXML public TextField LOGIN_AREA;
  @FXML public Label ERROR_AREA;

  @FXML
  void initialize() {
    instance = this;
    
    LOGIN_BUTTON.setOnAction(event -> {
      if (!Client.isConnected()) {
        showError("Отсутствует подключение к серверу авторизации...");
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
    Platform.runLater(() -> {
      ERROR_AREA.setText(text);
      ERROR_AREA.setVisible(true);
    });
  }
  
  public synchronized void hideErrors() {
    Platform.runLater(() -> 
      ERROR_AREA.setVisible(false));
  }
}