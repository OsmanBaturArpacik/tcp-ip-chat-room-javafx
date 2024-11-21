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

    private static Client instance;
    public static Client getInstance() {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }
    public Socket getClient() {
        return client;
    }

    public Client() {
        done = false;
    }

    @Override
    public void run() {
        try {
            client = new Socket("127.0.0.1", 8989);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            sendMessageToUI(GetData.nickname);

            if (App.getInMessageService() != null) {
                App.getInMessageService().setIn(in);
                new Thread(App.getInMessageService()).start();
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

