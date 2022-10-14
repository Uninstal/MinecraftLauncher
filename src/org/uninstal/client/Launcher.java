package org.uninstal.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.uninstal.client.util.Paths;

import java.net.URL;
import java.util.Objects;

public class Launcher extends Application {
  
  private static Stage stage;
  private static Scene scene;

  public static void main(String[] args) {
    launch();
  }
  
  @Override
  public void start(Stage primaryStage) throws Exception {
    // BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
    // ImageIO.write(image, "png", new File(Paths.getHomeLocation() + "/screenshot.png"));
    
    stage = primaryStage;
    URL fxmlFileUrl = Paths.getUrl("/assets/launcher.fxml");
    if (fxmlFileUrl == null) {
      System.out.println("File of launcher.fxml is null");
      System.exit(0);
      return;
    }
    Parent root = FXMLLoader.load(fxmlFileUrl);
    scene = new Scene(root);
    primaryStage.setTitle("NDAZ Official Launcher ©");
    primaryStage.setScene(scene);
    primaryStage.getIcons().add(new Image(Objects.requireNonNull(Launcher.class.getResourceAsStream("/assets/icon.png"))));
    primaryStage.show();
    primaryStage.setOnCloseRequest(e -> Client.getConnection().disconnect());
    Client.main();
  }
  
  public static void hide() {
    stage.close();
  }
  
  public static void show() {
    stage.show();
  }

  public static Scene getScene() {
    return scene;
  }

  public static Stage getStage() {
    return stage;
  }
}