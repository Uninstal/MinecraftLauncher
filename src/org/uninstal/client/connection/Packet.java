package org.uninstal.client.connection;

public class Packet {
  
  private final Connection connection;
  private final PacketType packetType;

  public Packet(Connection connection, PacketType packetType) {
    this.connection = connection;
    this.packetType = packetType;
  }

  public Connection getConnection() {
    return connection;
  }

  public PacketType getType() {
    return packetType;
  }

  @Override
  public String toString() {
    return "[type=" + packetType.getName() + "]";
  }
}