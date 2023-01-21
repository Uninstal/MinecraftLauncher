package org.uninstal.client.connection.impl;

import org.uninstal.client.connection.Connection;
import org.uninstal.client.connection.Packet;
import org.uninstal.client.connection.PacketReceivable;
import org.uninstal.client.connection.PacketType;
import org.uninstal.client.connection.download.DownloadProcessType;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PacketDownloadProcessImpl extends Packet implements PacketReceivable {
  
  public PacketDownloadProcessImpl(Connection connection) {
    super(connection, PacketType.DOWNLOAD_PROCESS_IMPL);
  }

  @Override
  public void receive(DataInputStream input) throws IOException {
    connection.createDownloadProccess(
      input.readInt(), 
      DownloadProcessType.valueOf(input.readUTF()),
      input.readInt(), 
      input.readBoolean()
    );
  }
}