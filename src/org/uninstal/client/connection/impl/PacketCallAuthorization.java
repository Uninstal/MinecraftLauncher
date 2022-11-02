package org.uninstal.client.connection.impl;

import org.uninstal.client.connection.Connection;
import org.uninstal.client.connection.Packet;
import org.uninstal.client.connection.PacketSentable;
import org.uninstal.client.connection.PacketType;

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
  public void send(DataOutputStream output) throws IOException {
    output.writeUTF(login);
    output.writeUTF(password);
    output.flush();
  }
}
