package org.uninstal.client.connection.download;

import org.uninstal.client.fxml.PlayScene;

public class DownloadProcessUpdate extends DownloadProcess {

  public DownloadProcessUpdate(int processId, int total, boolean replace) {
    super(processId, total, replace);
    PlayScene.getInstance().PLAY_BUTTON.setDisable(true);
  }

  @Override
  protected void onDownloadEnd() {
    PlayScene.getInstance().PLAY_BUTTON.setDisable(false);
    PlayScene.getInstance().showStatus("Обновление завершено");
  }

  @Override
  protected void onBuildFile() {
    PlayScene.getInstance().showStatus("Обновление: " + (getReaded() * 100 / getTotal()) + "%");
  }
}