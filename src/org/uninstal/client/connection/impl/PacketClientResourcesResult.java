package org.uninstal.client.connection.impl;

import org.uninstal.client.Client;
import org.uninstal.client.Launcher;
import org.uninstal.client.connection.Connection;
import org.uninstal.client.connection.Packet;
import org.uninstal.client.connection.PacketReceivable;
import org.uninstal.client.connection.PacketType;
import org.uninstal.client.fxml.PlayScene;

import java.io.DataInputStream;
import java.io.IOException;

public class PacketClientResourcesResult extends Packet implements PacketReceivable {
  
  public PacketClientResourcesResult(Connection connection) {
    super(connection, PacketType.CLIENT_RESOURCES_RESULT);
  }

  @Override
  public void receive(DataInputStream input) throws IOException {
    String resultString = input.readUTF();
    try {
      ClientResourcesResult result = ClientResourcesResult.valueOf(resultString);
      if (result == ClientResourcesResult.VALID) {
        PlayScene.getInstance().hideText();
        Launcher.launchMinecraft(Client.getNickname());
        Launcher.hide();
      } else if (result == ClientResourcesResult.BAD) {
        PlayScene.getInstance().showError("Ваша сборка повреждена или не является актуальной");
      } else if (result == ClientResourcesResult.UPDATE) {
        PlayScene.getInstance().showStatus("Обновление клиента...");
      }
    } catch(IllegalArgumentException e) {
      PlayScene.getInstance().showError("Неверный тип результата: " + resultString);
    }
  }
}