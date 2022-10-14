package org.uninstal.client.fxml;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.uninstal.client.Account;
import org.uninstal.client.Client;
import org.uninstal.client.minecraft.Minecraft;

public class FxmlController {
  
  private static ProgressBarController progressBarController;
  
  @FXML public Button loginButton;
  @FXML public PasswordField passwordArea;
  @FXML public TextField loginArea;
  @FXML public Label label;
  @FXML public ProgressBar progressBar;

  @FXML
  void initialize() {
    assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'launcher.fxml'.";
    assert loginArea != null : "fx:id=\"loginArea\" was not injected: check your FXML file 'launcher.fxml'.";
    assert passwordArea != null : "fx:id=\"passwordArea\" was not injected: check your FXML file 'launcher.fxml'.";
    progressBarController = new ProgressBarController(label, progressBar);
    
    // loginButton.setDisable(true);
    loginButton.setOnAction(event -> {
      Client.setAccount(new Account("Uninstal"));
      new Minecraft().launch();
    });
  }

  public static ProgressBarController getProgressBarController() {
    return progressBarController;
  }
}