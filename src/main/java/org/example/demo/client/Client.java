package org.example.demo.client;

import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {

    private Socket client;
    private ChatController chatController;
//    private static final String HOSTNAME = “localhost”;
//    private static final int PORT = 1234;
    private static BufferedReader in;
    private static PrintWriter out;
    private boolean done;
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public Client(ChatController chatController) {
        this.chatController = chatController;
        done = false;
    }

    @Override
    public void run() {
        try {
            client = new Socket("127.0.0.1", 8989);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

//            sendMessage(nickname);

            String inMessage;
            while ((inMessage = in.readLine()) != null) {
                System.out.println("Gelen mesaj: " + inMessage);

                final String str = inMessage + "\n";
                Platform.runLater(() -> {
                    if (chatController != null) {
                        chatController.appendMessage(str);
                    }
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

    public static void sendMessage(String message) {
        if (out != null && !message.isEmpty()) {
            out.println(message);
        }
    }

}

