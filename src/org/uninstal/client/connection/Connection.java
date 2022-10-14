package org.uninstal.client.connection;

import org.uninstal.client.Launcher;
import org.uninstal.client.connection.download.DownloadProcess;
import org.uninstal.client.connection.impl.PacketDownloadFile;
import org.uninstal.client.connection.impl.PacketDownloadProcessDown;
import org.uninstal.client.connection.impl.PacketDownloadProcessImpl;
import org.uninstal.client.connection.impl.PacketResultAuthorization;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
  private InputStream input;
  private OutputStream output;
  
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
      while (!isConnected() && Launcher.getStage().isShowing()) {
        try {
          this.socket = new Socket();
          this.socket.connect(new InetSocketAddress(ip, port), 1500);
          this.input = socket.getInputStream();
          this.output = socket.getOutputStream();
          state.accept(true);
        } catch (Exception e) {
          state.accept(false);
        }
        attempts++;
      }
    }, "Connecting").start();
  }
  
  public void runThread() {
    if (socket == null || !socket.isConnected() || socket.isClosed()) {
      System.out.println("Failed to run connection thread!");
      System.out.println("Socket: " + socket);
      System.out.println("Connected: " + (socket != null && socket.isConnected()));
      System.out.println("Closed: " + (socket != null && socket.isClosed()));
      return;
    }
    this.thread = new Thread(() -> {
      System.out.println(321);
      while (!thread.isInterrupted()) {
        
        try {
          Packet packet = readPacket();
          if(packet != null) {
            try {
              ((PacketReceivable) packet).receive(input);
            } catch(Exception e) {
              // Пакет не был прочитан
            }
          }
        } catch (Exception e) {
          disconnect();
          return;
        }
      }
    });
    this.thread.start();
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
  
  public void sendPacket(PacketSentable packet) {
    try {
      packet.send(output);
    } catch(IOException e) {
      disconnect();
    }
  }
  
  private Packet readPacket() {
    
    try {
      
      DataInputStream data = new DataInputStream(input);
      String packetName = data.readUTF();
      PacketType packetType;
      
      try {
        packetType = PacketType.valueOf(packetName.toUpperCase());
      } catch(Exception e) {
        System.out.println("Неизвестный тип пакета: " + packetName);
        return null;
      }
      
      if (packetType == PacketType.RESULT_AUTHORIZATION) return new PacketResultAuthorization(this);
      else if (packetType == PacketType.DOWNLOAD_PROCESS_IMPL) return new PacketDownloadProcessImpl(this);
      else if (packetType == PacketType.DOWNLOAD_FILE) return new PacketDownloadFile(this);
      else if (packetType == PacketType.DOWNLOAD_PROCESS_DOWN) return new PacketDownloadProcessDown(this);
      else return null;
      
    } catch (Exception e) {
      e.printStackTrace();
      disconnect();
    }
    
    return null;
  }
  
  public InputStream getInputStream() {
    return input;
  }

  public OutputStream getOutputStream() {
    return output;
  }

  public int getPort() {
    return port;
  }

  public String getIp() {
    return ip;
  }
  
  public boolean isConnected() {
    return socket != null && socket.isConnected();
  }
}