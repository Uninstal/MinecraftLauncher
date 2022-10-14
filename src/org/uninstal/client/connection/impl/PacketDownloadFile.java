package org.uninstal.client.connection.impl;

import org.uninstal.client.Client;
import org.uninstal.client.connection.Connection;
import org.uninstal.client.connection.Packet;
import org.uninstal.client.connection.PacketReceivable;
import org.uninstal.client.connection.PacketType;
import org.uninstal.client.connection.download.DownloadProcess;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PacketDownloadFile extends Packet implements PacketReceivable {

  public PacketDownloadFile(Connection connection) {
    super(connection, PacketType.DOWNLOAD_FILE);
  }

  @Override
  public void receive(InputStream input) throws IOException {
    DataInputStream data = new DataInputStream(input);
    int processId = data.readInt();
    DownloadProcess process = Client.getConnection().getDownloadProcess(processId);
    
    if (process != null) {
      String folder = data.readUTF();
      String fileName = data.readUTF();
      process.buildFile(data, folder, fileName);
    }
  }
}