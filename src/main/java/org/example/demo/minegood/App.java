package org.example.demo.minegood;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class App extends Application implements Initializable {
    private static Scene scene;
    private static Stage stage;
    private static double x = 0;
    private static double y = 0;

    private static ChatController chatController;
    private static Client client;

    public static Client getClient() {
        return client;
    }

    public static void setChatController(ChatController chatController) {
        App.chatController = chatController;
    }

    public static ChatController getChatController() {
        return chatController;
    }

    @Override
    public void start(Stage stage) throws IOException {
        App.stage = stage;
        App.scene = new Scene(loadFXML("chat"));
        stage.setScene(scene);
        setRootAsDraggable();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();

        client = new Client(chatController);
        new Thread(client).start();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml+".fxml"));
        chatController = fxmlLoader.getController();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}