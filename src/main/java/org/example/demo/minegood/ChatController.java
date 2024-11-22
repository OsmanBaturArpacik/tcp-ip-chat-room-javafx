package org.example.demo.minegood;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.example.demo.client.Client.sendMessage;

public class ChatController implements Initializable {

    @FXML
    private TextArea chatHistory;

    @FXML
    private Button closeBtn;

    @FXML
    private AnchorPane mainForm;

    @FXML
    private TextField message;

    @FXML
    private Button minimizeBtn;

    @FXML
    private Label nicknameLabel;

    @FXML
    private Button quitBtn;

    @FXML
    private Button sentBtn;

    @FXML
    void quitChat() throws IOException {
        System.out.println("/quit");
        System.exit(1);
        // exit from form to do back
        // /nick
    }

    @FXML
    void sentMessage() {

        String messageText = message.getText().trim();
        if (messageText.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please write a message.");
            alert.showAndWait();
            return;
        }

        sendMessage(messageText);
//        appendMessage(GetData.nickname + "(me): " + messageText);
        message.clear();
    }

    public void appendMessage(String newMessage) {
        System.out.println("Yeni mesaj ekleniyor: " + newMessage);
        chatHistory.appendText(newMessage + "\n");
    }

    public void displayUsername() {
        if (App.getClient() != null) {
            nicknameLabel.setText(App.getClient().getNickname());
        }
    }
    public void close() {
        System.exit(0);
    }
    public void minimize() {
        Stage stage = (Stage) mainForm.getScene().getWindow();
        stage.setIconified(true);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        App.setChatController(this);
        displayUsername();
    }

}
