package org.uninstal.client.fxml;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import org.uninstal.client.Client;
import org.uninstal.client.connection.impl.PacketClientResourses;

public class PlayScene {
  
  public static PlayScene instance;
  
  public Button PLAY_BUTTON;
  public Label ERROR_AREA;
  public Label PROGRESS_AREA;
  public ProgressBar PROGRESS;
  
  public Label nickname;
  public Label balance;
  public Label group;

  @FXML
  void initialize() {
    instance = this;
    
    PLAY_BUTTON.setOnAction(event -> Client.getConnection().sendPacket(new PacketClientResourses(Client.getConnection())));
  }

  public static PlayScene getInstance() {
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