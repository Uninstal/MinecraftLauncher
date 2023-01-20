package org.uninstal.client.connection.impl;

import org.uninstal.client.connection.Connection;
import org.uninstal.client.connection.Packet;
import org.uninstal.client.connection.PacketSentable;
import org.uninstal.client.connection.PacketType;
import org.uninstal.client.fxml.AuthScene;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PacketCallAuthorization extends Packet implements PacketSentable {
  
  private final String login;
  private final String password;
  
  public PacketCallAuthorization(Connection connection, String login, String password) {
    super(connection, PacketType.CALL_AUTHORIZATION);
    this.login = login;
    this.password = password;
  }

  @Override
  public boolean isCancelled() {
    if (login.isEmpty())
      AuthScene.getInstance().showError("Введите никнейм");
    else if (password.isEmpty())
      AuthScene.getInstance().showError("Введите пароль");
    else return false;
    return true;
  }

  @Override
  public void send(DataOutputStream output) throws IOException {
    if (login.isEmpty())
      AuthScene.getInstance().showError("Введите никнейм");
    else if (password.isEmpty())
      AuthScene.getInstance().showError("Введите пароль");
    else {
      output.writeUTF(login);
      output.writeUTF(password);
      output.flush();
    }
  }
}
