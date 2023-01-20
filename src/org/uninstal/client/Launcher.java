package org.uninstal.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import org.uninstal.client.fxml.AuthScene;
import org.uninstal.client.fxml.PlayScene;
import org.uninstal.client.minecraft.Minecraft;
import org.uninstal.client.util.Paths;

import java.awt.*;
import java.net.URL;
import java.util.Objects;

public class Launcher extends Application {
  
  private static Minecraft minecraft;
  private static double width;
  private static double height;
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
    URL authSceneURL = Paths.getUrl("/assets/auth.fxml");
    URL playSceneURL = Paths.getUrl("/assets/play.fxml");
    if (authSceneURL == null || playSceneURL == null) {
      System.out.println("File broken.");
      System.exit(0);
      return;
    }
    
    authScene = new Scene(FXMLLoader.load(authSceneURL));
    playScene = new Scene(FXMLLoader.load(playSceneURL));
    // Создание масштабированного разрешения для монитора пользователя.
    createScaling();
    // Применяем масштабирование к сценам.
    applyScaling(AuthScene.getInstance().PANE);
    applyScaling(PlayScene.getInstance().PANE);
    
    primaryStage.setTitle("NDAZ Official Launcher ©");
    primaryStage.setScene(Launcher.authScene);
    primaryStage.getIcons().add(new Image(Objects.requireNonNull(Launcher.class.getResourceAsStream("/assets/icon.png"))));
    primaryStage.setResizable(false);
    primaryStage.sizeToScene();
    primaryStage.show();
    primaryStage.setOnCloseRequest(e -> {
      Client.getConnection().disconnect();
      Platform.exit();
    });
    Client.main();
  }
  
  public static void launchMinecraft(String nickname) {
    if (!isMinecraftLaunched()) {
      minecraft = new Minecraft(nickname);
      minecraft.launch();
    }
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
      stage.sizeToScene();
      stage.show();
    });
  }

  /**
   * Инициализация размеров окна лаунчера под разные разрешения.
   * Цифры были вычислены опытным путем для правильного соотношения сторон.
   */
  private static void createScaling() {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    width = Math.floor(screenSize.width / 2d);
    height = Math.floor(screenSize.height / 2d);
    System.out.println("Laucnher screen size: " + ((int) width) + "x" + ((int) height));
  }

  /**
   * Применение размеров к указанному окну.
   */
  private static void applyScaling(Pane pane) {
    pane.getTransforms().setAll(new Scale(width / 960d, height / 540d, 0d, 0d));
    pane.setMinSize(0d, 0d);
    pane.setMaxSize(9999d, 9999d);
    pane.setPrefSize(width, height);
  }
}