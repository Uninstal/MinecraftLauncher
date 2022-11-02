package org.uninstal.client.connection.impl;

import org.uninstal.client.Client;
import org.uninstal.client.Launcher;
import org.uninstal.client.connection.Connection;
import org.uninstal.client.connection.Packet;
import org.uninstal.client.connection.PacketReceivable;
import org.uninstal.client.connection.PacketType;
import org.uninstal.client.fxml.AuthScene;
import org.uninstal.client.fxml.PlayScene;

import java.io.DataInputStream;
import java.io.IOException;

public class PacketAuthorizationResult extends Packet implements PacketReceivable {
  
  private AuthorizationResult result;
  
  public PacketAuthorizationResult(Connection connection) {
    super(connection, PacketType.RESULT_AUTHORIZATION);
  }
  
  public AuthorizationResult getResult() {
    return result;
  }

  @Override
  public void receive(DataInputStream input) throws IOException {
    String resultString = input.readUTF();
    
    try {
      this.result = AuthorizationResult.valueOf(resultString);
    } catch(IllegalArgumentException e) {
      System.out.println("Unknown authorization result: " + resultString);
      return;
    }
    
    AuthScene fxml = AuthScene.getInstance();
    if (result == AuthorizationResult.SUCCESS) {
      String nickname = fxml.LOGIN_AREA.getText();
      Client.setNickname(nickname);
      PlayScene.getInstance().nickname.setText(
        PlayScene.getInstance().nickname.getText().replace("%name%", nickname));
      Launcher.hide();
      Launcher.showPlayScene();
    } else if (result == AuthorizationResult.DENY_LOGIN) {
      fxml.showError("Данного пользователя не существует");
    } else if (result == AuthorizationResult.DENY_PASSWORD) {
      fxml.showError("Пароль неверный");
    } else if (result == AuthorizationResult.TOO_MANY_ATTEMPTS) {
      fxml.showError("Слишком много попыток авторизации, попробуйте через 3 минуты...");
    }
  }
}
