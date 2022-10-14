package org.uninstal.client.connection;

import java.io.IOException;
import java.io.OutputStream;

public interface PacketSentable {
  
  void send(OutputStream output) throws IOException;
}