package org.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import org.example.demo.client.*;

public class App extends Application{
    private static Scene scene;
    private static Stage stage;
    private static double x = 0;
    private static double y = 0;
    private static InMessageService inMessageService;
    private static ChatController chatController;
    private static LoginController loginController;
    private static Client client;

    public static Client getClient() {
        return client;
    }
    public static ChatController getChatController() {
        return chatController;
    }
    public static LoginController getLoginController() {
        return loginController;
    }
    public static InMessageService getInMessageService() {
        return inMessageService;
    }

    @Override
    public void start(Stage stage) throws IOException {
        // FXML dosyalarını yükle
        FXMLLoader loginLoader = new FXMLLoader(App.class.getResource("login.fxml"));
        Parent loginRoot = loginLoader.load();
        loginController = loginLoader.getController();

        FXMLLoader chatLoader = new FXMLLoader(App.class.getResource("chat.fxml"));
        Parent chatRoot = chatLoader.load();
        chatController = chatLoader.getController();

        App.stage = stage;
        App.scene = new Scene(loadFXML("login"));
        stage.setScene(scene);
        setRootAsDraggable();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();

        inMessageService = new InMessageService();
        client = Client.getInstance();
        new Thread(client).start();
    }

    public static void setRoot(String fxml) throws IOException {
        App.scene.setRoot(loadFXML(fxml));
        App.scene.getWindow().sizeToScene();
        App.scene.getWindow().centerOnScreen();
        setRootAsDraggable();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml+".fxml"));
        return fxmlLoader.load();
    }

   public static void setRootAsDraggable() {
       Parent root = App.stage.getScene().getRoot();
       root.setOnMousePressed((MouseEvent event) -> {
       x = event.getX();
       y = event.getY();
       root.requestFocus();
       });

       root.setOnMouseDragged((MouseEvent event) -> {
       App.stage.setX(event.getScreenX() - x);
       App.stage.setY(event.getScreenY() - y);
       App.stage.setOpacity(.8);
       });

       root.setOnMouseReleased((MouseEvent event) -> {
       App.stage.setOpacity(1);
       });
   }

    public static void main(String[] args) {
        launch();
    }
}