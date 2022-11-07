package org.uninstal.client.connection;

import org.uninstal.client.Launcher;
import org.uninstal.client.connection.download.DownloadProcess;
import org.uninstal.client.connection.impl.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Connection {
  
  private final Map<Integer, DownloadProcess> downloadProcessMap = new HashMap<>();
  private final String ip;
  private final int port;
  
  private int attempts;
  private Thread thread;
  private Socket socket;
  private DataInputStream input;
  private DataOutputStream output;
  
  public Connection(String ip, int port) {
    this.attempts = 0;
    this.ip = ip;
    this.port = port;
  }

  public int getAttempts() {
    return attempts;
  }

  public void tryConnect(Consumer<Boolean> state) {
    new Thread(() -> {
      while (!isConnected() && Launcher.isShowing()) {
        attempts++;
        try {
          this.socket = new Socket();
          this.socket.connect(new InetSocketAddress(ip, port), 1500);
          this.input = new DataInputStream(socket.getInputStream());
          this.output = new DataOutputStream(socket.getOutputStream());
          state.accept(true);
        } catch (Exception e) {
          state.accept(false);
        }
      }
    }, "Connecting").start();
  }
  
  public void runThread() {
    if (!isConnected())
      System.out.println("Could not start ConnectionThread because socket is not connected");
    else if (thread != null && !thread.isInterrupted())
      System.out.println("Could not start ConnectionThread because it is already running");
    else {
      this.thread = new Thread(() -> {
        while (!thread.isInterrupted() && isConnected()) {
          try {
            Packet packet = readPacket();
            if(packet != null) {
              try {
                ((PacketReceivable) packet).receive(input);
              } catch(Exception ignored) {
                System.out.println("Packet " + packet.getType() + " was not read");
              }
            }
          } catch (Exception e) {
            disconnect();
            return;
          }
        }
        System.out.println("ConnectionThread has finished work");
      }, "ConnectionThread");
      this.thread.start();
    }
  }
  
  public void disconnect() {
    try {
      if (thread != null) thread.interrupt();
      if (input != null) input.close();
      if (output != null) output.close();
      if (socket != null) socket.close();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  public DownloadProcess getDownloadProcess(int id) {
    return downloadProcessMap.get(id);
  }
  
  public void createDownloadProccess(int id, int totalFiles) {
    downloadProcessMap.put(id, new DownloadProcess(id, totalFiles));
  }
  
  public void downDownloadProccess(int id) {
    if (downloadProcessMap.containsKey(id))
      downloadProcessMap.remove(id).down();
  }
  
  public void sendPacket(Packet packet) {
    try {
      if (!(packet instanceof PacketSentable))
        throw new IllegalArgumentException("Packet " + packet + " is not sentable");
      output.writeUTF(packet.getType().getName());
      ((PacketSentable) packet).send(output);
    } catch(IOException e) {
      disconnect();
    }
  }
  
  private Packet readPacket() {
    try {
      String packetName = input.readUTF();
      PacketType packetType;

      try {
        packetType = PacketType.valueOf(packetName.toUpperCase());
      } catch(Exception e) {
        System.out.println("Unknown packet type: " + packetName);
        return null;
      }

      System.out.println(packetType.getName());
      if (packetType == PacketType.RESULT_AUTHORIZATION) return new PacketAuthorizationResult(this);
      else if (packetType == PacketType.DOWNLOAD_PROCESS_IMPL) return new PacketDownloadProcessImpl(this);
      else if (packetType == PacketType.DOWNLOAD_FILE) return new PacketDownloadFile(this);
      else if (packetType == PacketType.DOWNLOAD_PROCESS_DOWN) return new PacketDownloadProcessDown(this);
      else if (packetType == PacketType.CLIENT_RESOURCES_RESULT) return new PacketClientResourcesResult(this);
      
    } catch (IOException ignored) {
    } catch (Exception e) {
      e.printStackTrace();
      disconnect();
    }
    
    return null;
  }
  
  public boolean isConnected() {
    return socket != null && socket.isConnected() && !socket.isClosed();
  }
}