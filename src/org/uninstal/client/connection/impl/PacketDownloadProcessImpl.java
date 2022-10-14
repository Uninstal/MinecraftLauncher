package org.uninstal.client.connection.impl;

import org.uninstal.client.connection.Connection;
import org.uninstal.client.connection.Packet;
import org.uninstal.client.connection.PacketReceivable;
import org.uninstal.client.connection.PacketType;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PacketDownloadProcessImpl extends Packet implements PacketReceivable {
  
  public PacketDownloadProcessImpl(Connection connection) {
    super(connection, PacketType.DOWNLOAD_PROCESS_IMPL);
  }

  @Override
  public void receive(InputStream input) throws IOException {
    
    DataInputStream data = new DataInputStream(input);
    int proccessId = data.readInt();
    int totalFiles = data.readInt();
    getConnection().createDownloadProccess(proccessId, totalFiles);
  }
}