package org.uninstal.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.uninstal.client.minecraft.Minecraft;
import org.uninstal.client.util.Paths;

import java.net.URL;
import java.util.Objects;

public class Launcher extends Application {
  
  private static Minecraft minecraft;
  private static Stage stage;
  private static Scene authScene;
  private static Scene playScene;

  public static void main(String[] args) {
    launch();
  }
  
  @Override
  public void start(Stage primaryStage) throws Exception {
    // BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
    // ImageIO.write(image, "png", new File(Paths.getHomeLocation() + "/screenshot.png"));
    
    // Избегание завершения потока сцен.
    Platform.setImplicitExit(false);
    
    stage = primaryStage;
    URL authScene = Paths.getUrl("/assets/auth.fxml");
    URL playScene = Paths.getUrl("/assets/play.fxml");
    if (authScene == null || playScene == null) {
      System.out.println("File broken.");
      System.exit(0);
      return;
    }
    
    Launcher.authScene = new Scene(FXMLLoader.load(authScene));
    Launcher.playScene = new Scene(FXMLLoader.load(playScene));
    primaryStage.setTitle("NDAZ Official Launcher ©");
    primaryStage.setScene(Launcher.authScene);
    primaryStage.getIcons().add(new Image(Objects.requireNonNull(Launcher.class.getResourceAsStream("/assets/icon.png"))));
    primaryStage.show();
    primaryStage.setOnCloseRequest(e -> {
      Client.getConnection().disconnect();
      Platform.exit();
    });
    Client.main();
  }
  
  public static void launchMinecraft(String nickname) {
    if (isMinecraftLaunched()) return;
    minecraft = new Minecraft(nickname);
    minecraft.launch();
  }
  
  public static void hide() {
    Platform.runLater(() -> stage.hide());
  }
  
  public static void show() {
    Platform.runLater(() -> stage.show());
  }
  
  public static boolean isShowing() {
    return stage.isShowing();
  }

  public static Minecraft getMinecraft() {
    return minecraft;
  }
  
  public static boolean isMinecraftLaunched() {
    return minecraft != null;
  }
  
  public static void onMinecraftClosed() {
    minecraft = null;
  }

  public static void showPlayScene() {
    Platform.runLater(() -> {
      stage.setScene(playScene);
      stage.show();
    });
  }
}