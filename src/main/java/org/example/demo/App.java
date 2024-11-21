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
    private static OutMessageService outMessageService;
    private static MessageService messageService;
    private static ChatController chatController;

    public static ChatController getChatController() {
        return chatController;
    }

    public static MessageService getMessageService() {
        return messageService;
    }

    @Override
    public void start(Stage stage) throws IOException {
        App.stage = stage;
        App.scene = new Scene(loadFXML("login"));
        stage.setScene(scene);
        setRootAsDraggable();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();

        messageService = new MessageService(Client.getInstance());

        // OutMessageService'i başlatıyoruz
        outMessageService = new OutMessageService();
        // InMessageService için bir thread başlatıyoruz
        inMessageService = new InMessageService();
        Thread inThread = new Thread(inMessageService);
        inThread.start();

    }

    // GUI'den gelen mesajları OutMessageService ile sunucuya gönderiyoruz
    public static void sendMessageToServer(String message) {
        if (outMessageService != null) {
            outMessageService.sendMessage(message);
        }
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