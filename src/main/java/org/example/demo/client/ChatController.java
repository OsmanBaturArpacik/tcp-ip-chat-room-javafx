package org.example.demo.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.demo.App;

import static org.example.demo.client.Client.sendMessage;

public class ChatController implements Initializable {

    @FXML
    private TextArea chatHistory;

    @FXML
    private StackPane chatPane;

    @FXML
    private Button closeBtn;

    @FXML
    private Button closeBtn1;

    @FXML
    private Button loginBtn;

    @FXML
    private StackPane loginPane;

    @FXML
    private StackPane mainPane;

    @FXML
    private TextField message;

    @FXML
    private Button minimizeBtn;

    @FXML
    private TextField nickname;

    @FXML
    private Label nicknameLabel;

    @FXML
    private Button quitBtn;

    @FXML
    private Button sentBtn;


    @FXML
    void enterChat() throws IOException {
        Alert alert;

        if (nickname.getText().isEmpty()){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all necessary fields.");
            alert.showAndWait();
        }
        else {
            String nick = nickname.getText();
            App.getClient().setNickname(nick);
            new Thread(App.getClient()).start();
            loginPane.setVisible(false);
            displayUsername();
        }
    }


    @FXML
    void quitChat() throws IOException {
        System.out.println("/quit");
        loginPane.setVisible(true);
        // exit from form to do back
        // /nick /help /clear
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
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        App.setChatController(this);
        displayUsername();
    }

}
