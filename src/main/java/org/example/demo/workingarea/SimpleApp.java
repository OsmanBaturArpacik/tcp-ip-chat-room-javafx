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
    private static Arayuz arayuz;  // Arayuz sınıfını burada tanımlıyoruz.

    // TextArea'ya metin eklemek için fonksiyon
    public static void appendText(String text) {
        textArea.appendText(text + "\n");
    }

    @Override
    public void start(Stage primaryStage) {
        // TextArea: Ortada yer alacak
        textArea.setEditable(false);  // Kullanıcı metin giremesin
        textArea.setWrapText(true);   // Satır sonu
        textArea.setPadding(new Insets(10)); // İç padding ekledik

        // TextField: Kullanıcıdan girdi alacak
        textField.setPadding(new Insets(10)); // İç padding ekledik

        // Button: TextField'dan metin eklemek için
        button.setPadding(new Insets(10)); // İç padding ekledik

        // Button'a tıklandığında, TextField'dan alınan metni TextArea'ya ekler
        button.setOnAction(e -> {
            String inputText = textField.getText();
            if (!inputText.isEmpty()) {
                // Mesaj gönder
                arayuz.sendMessage(inputText);
                textField.clear(); // TextField'ı temizler
            }
        });

        // Enter tuşu ile de tetikleme
        textField.setOnAction(e -> {
            String inputText = textField.getText();
            if (!inputText.isEmpty()) {
                // Mesaj gönder
                arayuz.sendMessage(inputText);
                textField.clear(); // TextField'ı temizler
            }
        });

        // TextField ve Button'ı bir HBox içinde yan yana yerleştiriyoruz
        HBox inputBox = new HBox(10, textField, button);
        inputBox.setAlignment(Pos.CENTER);

        // En dıştaki VBox'a padding ekliyoruz
        VBox vbox = new VBox(10, textArea, inputBox);
        vbox.setAlignment(Pos.CENTER);  // Öğeler ortalanacak
        vbox.setPadding(new Insets(20));  // En dışta padding

        // Scene ve Stage
        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setTitle("Simple JavaFX App");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Arayuz'u başlat
        arayuz = new Arayuz();  // Arayuz sınıfını başlatıyoruz
        new Thread(arayuz).start();  // Arayuz thread'ini başlatıyoruz
    }

    public static void main(String[] args) {
        launch(args);
    }
}
