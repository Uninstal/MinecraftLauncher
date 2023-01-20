package org.uninstal.client.connection.impl;

import org.uninstal.client.connection.Connection;
import org.uninstal.client.connection.Packet;
import org.uninstal.client.connection.PacketSentable;
import org.uninstal.client.connection.PacketType;
import org.uninstal.client.util.Paths;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PacketClientResources extends Packet implements PacketSentable {
  
  public PacketClientResources(Connection connection) {
    super(connection, PacketType.CLIENT_RESOURCES);
  }
  
  @Override
  public void send(DataOutputStream output) throws IOException {
    String home = Paths.getDefaultLocation();
    List<File> files = Paths.getFiles(home);
    System.out.println(files.size());
    
    output.writeInt(files.size());
    for (File file : files) {
      String folder = file.getParent().length() <= home.length()
        ? "" : file.getParent().substring(home.length()).replace("\\", "/");
      output.writeUTF(folder);
      output.writeUTF(file.getName());
      output.writeLong(file.length());
    }
    output.flush();
  }
}
