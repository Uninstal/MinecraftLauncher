package org.uninstal.client.fxml;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.uninstal.client.Client;
import org.uninstal.client.connection.impl.PacketClientResources;

public class PlayScene {
  
  public static PlayScene instance;
  private long lock = System.currentTimeMillis();
  
  public AnchorPane PANE;
  public Button PLAY_BUTTON;
  public Label ERROR_AREA;
  public Label PROGRESS_AREA;
  public Label NICKNAME;

  @FXML
  void initialize() {
    instance = this;
    
    PLAY_BUTTON.setOnAction(event -> {
      if (!Client.isConnected()) {
        showError("Отсутствует подключение к серверу авторизации...");
        lock(5000L);
        return;
      }
      
      showStatus("Проверка клиента игры...");
      Client.getConnection().sendPacket(new PacketClientResources(Client.getConnection()));
    });
  }

  public static PlayScene getInstance() {
    return instance;
  }
  
  public synchronized void setNickname(String nickname) {
    this.NICKNAME.setText(this.NICKNAME.getText().replace("$name", nickname));
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