package org.uninstal.client.fxml;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import org.uninstal.client.Client;
import org.uninstal.client.Launcher;

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
    
    PLAY_BUTTON.setOnAction(event -> {
      Launcher.launchMinecraft(Client.getNickname());
      Launcher.hide();
    });
  }

  public static PlayScene getInstance() {
    return instance;
  }
}