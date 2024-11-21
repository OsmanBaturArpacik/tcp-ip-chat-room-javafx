package org.example.demo.client;

import javafx.application.Platform;
import org.example.demo.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class InMessageService implements Runnable {
    private BufferedReader in;
    private Socket client;

    @Override
    public void run() {
        try {
            client = new Socket("127.0.0.1", 8888);  // Server'a bağlanıyoruz
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String inMessage;
            while ((inMessage = in.readLine()) != null) {
                System.out.println("Gelen mesaj: " + inMessage);

                // Mesajı UI'ye göndermek için Platform.runLater() kullanıyoruz
                final String message = inMessage;  // Mesajı final yapıyoruz
                Platform.runLater(() -> {
                    // UI'yi güncellemek için ilgili metodu çağırıyoruz
                    App.getMessageService().handleIncomingMessage(message);
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            if (in != null) in.close();
            if (client != null) client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}