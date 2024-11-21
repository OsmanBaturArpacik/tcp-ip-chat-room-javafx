package org.example.demo.client;

public class MessageService {
    private Client client;

    public MessageService(Client client) {
        this.client = client;
    }

    public void sendMessage(String message) {
        if (message != null && !message.isEmpty()) {
            client.sendMessageToUI(message);
        }
    }

    public void handleIncomingMessage(String message) {
        ChatController.getInstance().appendMessage(message);
    }
}
