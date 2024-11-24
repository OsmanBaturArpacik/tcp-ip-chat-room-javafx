package org.example.demo.workingarea;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SimpleApp extends Application {

    private static TextArea textArea = new TextArea();
    private static TextField textField = new TextField();
    private static Button button = new Button("Ekle");
    private static Arayuz arayuz;

    public static void appendText(String text) {
        textArea.appendText(text + "\n");
    }

    @Override
    public void start(Stage primaryStage) {
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPadding(new Insets(10));

        textField.setPadding(new Insets(10));

        button.setPadding(new Insets(10));

        button.setOnAction(e -> {
            String inputText = textField.getText();
            if (!inputText.isEmpty()) {
                arayuz.sendMessage(inputText);
                textField.clear();
            }
        });

        textField.setOnAction(e -> {
            String inputText = textField.getText();
            if (!inputText.isEmpty()) {
                arayuz.sendMessage(inputText);
                textField.clear();
            }
        });

        HBox inputBox = new HBox(10, textField, button);
        inputBox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(10, textArea, inputBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setTitle("Simple JavaFX App");
        primaryStage.setScene(scene);
        primaryStage.show();

        arayuz = new Arayuz();
        new Thread(arayuz).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
