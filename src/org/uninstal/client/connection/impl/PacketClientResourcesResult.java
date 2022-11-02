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
    boolean valid = input.readBoolean();
    if (valid) {
      Launcher.launchMinecraft(Client.getNickname());
      Launcher.hide();
    } else {
      PlayScene.getInstance().showError("Ваша сборка повреждена или не является актуальной");
    }
  }
}