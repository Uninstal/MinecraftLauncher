package org.uninstal.client.connection.impl;

import org.uninstal.client.connection.Connection;
import org.uninstal.client.connection.Packet;
import org.uninstal.client.connection.PacketSentable;
import org.uninstal.client.connection.PacketType;
import org.uninstal.client.util.Paths;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

public class PacketClientResourses extends Packet implements PacketSentable {
  
  public PacketClientResourses(Connection connection) {
    super(connection, PacketType.CLIENT_RESOURCES);
  }
  
  @Override
  public void send(DataOutputStream output) throws IOException {
    for (File file : Paths.getFiles(Paths.getHomeLocation()))
      output.writeUTF(file.getName());
    output.flush();
  }
}
