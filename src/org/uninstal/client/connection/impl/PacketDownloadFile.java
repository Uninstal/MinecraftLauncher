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
    DownloadProcess process = connection.getDownloadProcess(input.readInt());
    if (process != null) process.buildFile(input, input.readUTF(), input.readUTF());
  }
}