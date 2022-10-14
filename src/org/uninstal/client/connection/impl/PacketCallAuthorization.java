package org.uninstal.client.connection.impl;

import org.uninstal.client.connection.Connection;
import org.uninstal.client.connection.Packet;
import org.uninstal.client.connection.PacketSentable;
import org.uninstal.client.connection.PacketType;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PacketCallAuthorization extends Packet implements PacketSentable {
  
  private String login;
  private String password;
  
  public PacketCallAuthorization(Connection connection, String login, String password) {
    super(connection, PacketType.CALL_AUTHORIZATION);
  }
  
  @Override
  public void send(OutputStream output) throws IOException {
    DataOutputStream data = new DataOutputStream(output);
    data.writeUTF(getType().getName());
    data.writeUTF(login);
    data.writeUTF(password);
    data.flush();
  }
}
