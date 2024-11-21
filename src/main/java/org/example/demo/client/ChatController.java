package org.example.demo.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.demo.App;

public class ChatController implements Initializable {

    @FXML
    private TextArea chatHistory;

    @FXML
    private Button close;

    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField message;

    @FXML
    private Button minimize;

    @FXML
    private Label nickname;

    @FXML
    private Button quitBtn;

    @FXML
    private Button sentBtn;

//    private final StringProperty chatContent = new SimpleStringProperty("");


    @FXML
    void quitChat() throws IOException {
        System.out.println("/quit");
        App.setRoot("login");
        // exit from form to do back
        // /nick
    }

    @FXML
    void sentMessage() {

        String messageText = message.getText().trim();
        if (messageText.isEmpty()) {
            showError("Please write a message.");
            return;
        }
        App.getClient().sendMessageToUI(messageText);
        appendMessage(GetData.nickname + "(me): " + messageText);
        message.clear();
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void appendMessage(String newMessage) {
        System.out.println("Yeni mesaj ekleniyor: " + newMessage);
        chatHistory.appendText(newMessage + "\n");
    }

    public void displayUsername() {
        nickname.setText(GetData.nickname);
    }

    public void close() {
        System.exit(0);
    }
    
    public void minimize() {
        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayUsername();
//        chatHistory.textProperty().bind(chatContent);
    }

}
