package org.example.demo.client;

import javafx.application.Platform;
import org.example.demo.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private boolean done;

    private Thread incomingMessageThread;
    private static Client instance;
    public static Client getInstance() {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }

    public Client() {
        done = false;
    }

    @Override
    public void run() {
        try {
            client = new Socket("127.0.0.1", 8888);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            sendMessageToUI(GetData.nickname);

            String inMessage;
            while ((inMessage = in.readLine()) != null) {
                System.out.println(inMessage);
                // client'a yazdir
//                ChatController.getInstance().appendMessage(inMessage);
                final String message = inMessage; // Mesajı final yaparak Platform.runLater()'a gönderiyoruz
                Platform.runLater(() -> {
                    App.getMessageService().handleIncomingMessage(message);
                });
            }
        } catch (IOException e) {
            shutdown();
        }
    }

    public void shutdown() {
        done = true;
        try {
            in.close();
            out.close();
            if (!client.isClosed()) {
                client.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendMessageToUI(String message) {
        if (out != null && !message.isEmpty()) {
            out.println(message);
        }
    }

}

