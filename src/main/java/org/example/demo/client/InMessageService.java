package org.example.demo.client;

import javafx.application.Platform;
import org.example.demo.App;

import java.io.BufferedReader;
import java.io.IOException;

public class InMessageService implements Runnable {
    private BufferedReader in;

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            if (in == null)
                return;

            String inMessage;
            while ((inMessage = in.readLine()) != null) {
                System.out.println("Gelen mesaj: " + inMessage);

                final String str = inMessage + "\n";
                Platform.runLater(() -> {
                    App.getChatController().appendMessage(str);
                });
            }
        } catch (IOException e) {
            //TODO
        }
    }

    public void shutdown() {
        App.getClient().shutdown();
//        try {
//            if (in != null)
//                in.close();
//            if (client != null)
//                client.close();
//        } catch (IOException e) {
//          //TODO
//        }
    }
}