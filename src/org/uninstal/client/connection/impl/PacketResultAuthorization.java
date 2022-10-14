package org.uninstal.client.connection.impl;

import org.uninstal.client.connection.Connection;
import org.uninstal.client.connection.Packet;
import org.uninstal.client.connection.PacketReceivable;
import org.uninstal.client.connection.PacketType;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PacketResultAuthorization extends Packet implements PacketReceivable {
  
  private AuthorizationResult result;
  
  public PacketResultAuthorization(Connection connection) {
    super(connection, PacketType.RESULT_AUTHORIZATION);
  }
  
  public AuthorizationResult getResult() {
    return result;
  }

  @Override
  public void receive(InputStream input) throws IOException {
    DataInputStream data = new DataInputStream(input);
    String resultString = data.readUTF();
    
    try {
      this.result = AuthorizationResult.valueOf(resultString);
    } catch(IllegalArgumentException e) {
      System.out.println("Unknown authorization result: " + resultString);
      return;
    }
    
    if (result == AuthorizationResult.SUCCESS) {
      
    }
  }
}
