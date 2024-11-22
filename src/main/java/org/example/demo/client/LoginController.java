package org.example.demo.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.demo.App;

public class LoginController implements Initializable {


    @FXML
    private Button closeBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane mainForm;

    @FXML
    private TextField nickname;

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
            App.setRoot("chat");
        }
    }

    public void close() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainForm.setFocusTraversable(true);
    }
}