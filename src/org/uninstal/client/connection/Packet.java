package org.uninstal.client.connection;

public class Packet {
  
  protected final Connection connection;
  protected final PacketType packetType;

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
    return "Packet[type=" + packetType.getName() + "]";
  }
}