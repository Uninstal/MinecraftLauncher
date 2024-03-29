package org.uninstal.client.connection.impl;

import org.uninstal.client.Client;
import org.uninstal.client.Launcher;
import org.uninstal.client.LauncherProperties;
import org.uninstal.client.connection.Connection;
import org.uninstal.client.connection.Packet;
import org.uninstal.client.connection.PacketReceivable;
import org.uninstal.client.connection.PacketType;
import org.uninstal.client.fxml.AuthScene;

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
      Client.setNickname(fxml.LOGIN_AREA.getText());
      LauncherProperties.setLogin(fxml.LOGIN_AREA.getText());
      LauncherProperties.setPassword(fxml.PASSWORD_AREA.getText());
      Launcher.hide();
      Launcher.showPlayScene();
      return;
    } else if (result == AuthorizationResult.DENY_LOGIN) {
      fxml.showError("Пользователя не существует");
    } else if (result == AuthorizationResult.DENY_PASSWORD) {
      fxml.showError("Пароль неверный");
    } else if (result == AuthorizationResult.TOO_MANY_ATTEMPTS) {
      fxml.showError("Слишком много попыток авторизации, попробуйте через 3 минуты...");
    }
    fxml.LOGIN_BUTTON.setDisable(false);
  }
}
