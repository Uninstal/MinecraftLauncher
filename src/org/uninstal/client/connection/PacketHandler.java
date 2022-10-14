package org.uninstal.client.connection;

public interface PacketHandler {
  void onReceive(Packet packet);
}