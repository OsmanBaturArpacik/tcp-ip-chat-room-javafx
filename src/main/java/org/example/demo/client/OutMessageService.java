package org.example.demo.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class OutMessageService {
    private PrintWriter out;
    private Socket client;

    public OutMessageService() {
        try {
            client = new Socket("127.0.0.1", 8888);  // Sunucuya bağlanıyoruz
            out = new PrintWriter(client.getOutputStream(), true);  // Output stream'i başlatıyoruz
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // GUI'den gelen mesajı sunucuya gönderiyoruz
    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);  // Mesajı sunucuya gönderiyoruz
        }
    }

    public void shutdown() {
        try {
            if (out != null) out.close();
            if (client != null) client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}