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
  public void receive(DataInputStream input) throws IOException {
    int proccessId = input.readInt();
    int totalFiles = input.readInt();
    getConnection().createDownloadProccess(proccessId, totalFiles);
  }
}