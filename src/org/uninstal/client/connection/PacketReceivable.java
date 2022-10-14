package org.uninstal.client.connection;

import java.io.IOException;
import java.io.InputStream;

public interface PacketReceivable {
  
  void receive(InputStream input) throws IOException;
}