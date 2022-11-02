package org.uninstal.client.connection.impl;

import org.uninstal.client.Client;
import org.uninstal.client.connection.Connection;
import org.uninstal.client.connection.Packet;
import org.uninstal.client.connection.PacketReceivable;
import org.uninstal.client.connection.PacketType;
import org.uninstal.client.connection.download.DownloadProcess;

import java.io.DataInputStream;
import java.io.IOException;

public class PacketDownloadFile extends Packet implements PacketReceivable {

  public PacketDownloadFile(Connection connection) {
    super(connection, PacketType.DOWNLOAD_FILE);
  }
  
  @Override
  public void receive(DataInputStream input) throws IOException {
    int processId = input.readInt();
    DownloadProcess process = Client.getConnection().getDownloadProcess(processId);
    
    if (process != null) {
      String folder = input.readUTF();
      String fileName = input.readUTF();
      process.buildFile(input, folder, fileName);
    }
  }
}