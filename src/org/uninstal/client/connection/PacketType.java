package org.uninstal.client.connection;

public enum PacketType {
  
  CLIENT_RESOURCES("CLIENT_RESOURCES"),
  CLIENT_RESOURCES_RESULT("CLIENT_RESOURCES_RESULT"),
  CALL_AUTHORIZATION("CALL_AUTHORIZATION"),
  RESULT_AUTHORIZATION("RESULT_AUTHORIZATION"),
  DOWNLOAD_PROCESS_IMPL("DOWNLOAD_PROCESS_IMPL"),
  DOWNLOAD_FILE("DOWNLOAD_FILE"),
  DOWNLOAD_PROCESS_DOWN("DOWNLOAD_PROCESS_DOWN");

  private final String name;

  PacketType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}