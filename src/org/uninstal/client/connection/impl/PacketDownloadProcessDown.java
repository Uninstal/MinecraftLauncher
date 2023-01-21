package org.uninstal.client.connection.impl;

import org.uninstal.client.connection.Connection;
import org.uninstal.client.connection.Packet;
import org.uninstal.client.connection.PacketReceivable;
import org.uninstal.client.connection.PacketType;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PacketDownloadProcessDown extends Packet implements PacketReceivable {

  public PacketDownloadProcessDown(Connection connection) {
    super(connection, PacketType.DOWNLOAD_PROCESS_DOWN);
  }

  @Override
  public void receive(DataInputStream input) throws IOException {
    connection.downDownloadProccess(input.readInt());
  }
}