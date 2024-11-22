package org.example.demo.workingarea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Arayuz implements Runnable {

    private Socket client;
    private static BufferedReader in;
    private static PrintWriter out;
    private boolean done;

    public Arayuz() {
        done = false;
    }

    @Override
    public void run() {
        try {
            client = new Socket("127.0.0.1", 8989); // Bağlantı adresi ve port
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("Bağlantı sağlandı!");

            String inMessage;
            while ((inMessage = in.readLine()) != null) {
                // Soketten gelen mesajı al ve GUI'ye yaz
                System.out.println("Gelen mesaj: " + inMessage);
                SimpleApp.appendText(inMessage);  // Gelen mesajı GUI'ye ekle
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
            out.println(message);  // Mesajı soket üzerinden gönder
        }
    }
}
