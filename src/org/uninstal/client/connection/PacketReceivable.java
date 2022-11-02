package org.uninstal.client.connection;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public interface PacketReceivable {
  
  void receive(DataInputStream input) throws IOException;
}