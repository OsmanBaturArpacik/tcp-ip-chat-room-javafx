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
    private final ChatController chatController;
//    private static final String HOSTNAME = “localhost”;
//    private static final int PORT = 1234;
    private static BufferedReader in;
    private static PrintWriter out;
    private boolean done;
    private static String nickname = "";

    public void setNickname(String nickname) {
        Client.nickname = nickname;
    }

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

            String inMessage;
            while ((inMessage = in.readLine()) != null) {
                if (inMessage.matches("(?i)^Please enter a nickname:\\s*$") && nickname != null) {
                    System.out.println("Nickname isteniyor, gönderiliyor...");
                    System.out.println(nickname);
                    sendMessage(nickname);
                } else {
                    chatController.appendMessage(inMessage);
                }
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

