package org.uninstal.client.fxml;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class ProgressBarController {
  
  private final Label label;
  private final ProgressBar progressBar;
  
  public ProgressBarController(Label label, ProgressBar progressBar) {
    this.label = label;
    this.progressBar = progressBar;
    this.label.setVisible(false);
    this.progressBar.setVisible(false);
  }

  public synchronized void setText(String text) {
    Platform.runLater(() -> {
      if (!progressBar.isVisible())
        progressBar.setVisible(true);
      if (!label.isVisible())
        label.setVisible(true);
      label.setText(text);
    });
  }
  
  public synchronized void disable() {
    Platform.runLater(() -> {
      label.setVisible(false);
      progressBar.setVisible(false);
    });
  }
}