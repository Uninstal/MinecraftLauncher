package org.uninstal.client.connection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public interface PacketSentable {
  
  void send(DataOutputStream output) throws IOException;
  
  default boolean isCancelled() {
    return false;
  }
}