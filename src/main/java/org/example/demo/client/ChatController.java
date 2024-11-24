package org.example.demo.client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.demo.App;

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
    void enterChat() {
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
            displayNickname();
        }
    }


    @FXML
    void quitChat() {
        System.out.println("/quit");
        App.getClient().sendMessage("/quit");
        loginPane.setVisible(true);
        App.getClient().setNickname(null);
    }

    @FXML
    void sendChatMessage() {
        String messageText = message.getText().trim();
        if (messageText.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please write a message.");
            alert.showAndWait();
            return;
        }
        else if (messageText.startsWith("/nick ")) {
            App.getClient().sendMessage(messageText);
            String[] messageSplit = messageText.split(" ", 2);
            App.getClient().setNickname(messageSplit[1]);
            displayNickname();
        }
        else if (messageText.startsWith("/clear")) {
            chatHistory.clear();
        }
        else if (messageText.startsWith("/quit")) {
            App.getClient().sendMessage("/quit");
        }
        else {
            App.getClient().sendMessage(messageText);
        }
        message.clear();
    }

    public void appendMessage(String newMessage) {
        chatHistory.appendText(newMessage + "\n");
    }

    public void displayNickname() {
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
        loginPane.setVisible(true);
        App.setChatController(this);
        displayNickname();
    }

}
