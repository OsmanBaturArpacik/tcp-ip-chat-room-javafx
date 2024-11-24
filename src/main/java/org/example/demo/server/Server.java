package org.example.demo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    // Server'a baglanan tum cihazlar listesi
    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;

    public Server() {
        done = false;
        connections = new ArrayList<>();
    }
    @Override
    public void run() {
        try {
            // server portunu belirliyoruz
            server = new ServerSocket(8989);
            pool = Executors.newCachedThreadPool();
            while(!done) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connections.add(handler);
                pool.execute(handler);
            }
        } catch (Exception e) {
            System.out.println("Error while starting server: " + e.getMessage());
            shutdown();
        }
    }

    public void broadcast(String message) {
        for (ConnectionHandler con : connections) {
            if (con != null) {
                con.sendMessage(message);
            }
        }
    }

    public void shutdown() {
        try {
            done = true;
            if (server != null && !server.isClosed()) {
                server.close();
            }
            for (ConnectionHandler con : connections) {
                con.shutdown();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // surekli http isteklerini dinler
    class ConnectionHandler implements Runnable {
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String nickname;

        public ConnectionHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out.println("Please enter a nickname: ");
                nickname = in.readLine();
                System.out.println(nickname + " connected to chat!");
                broadcast(nickname + " joined the chat!");
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("/nick ")) {
                        String[] messageSplit = message.split(" ", 2);
                        if (messageSplit.length == 2) {
                            broadcast(nickname + " renamed themselves to " + messageSplit[1]);
                            System.out.println(nickname + " renamed themselves to " + messageSplit[1]);
                            nickname = messageSplit[1];
                            out.println("Succesfully changed nickname to " + nickname);
                        } else {
                            out.println("No nickname provided!");
                        }
                    } else if (message.startsWith("/quit")) {
                        broadcast(nickname + " left the chat!");
                        System.out.println(nickname + " left the chat");
                        shutdown();
                    } else if (message.startsWith("/help")) {
                        // TODO: help, /quit, /nick x, /clear
                    } else {
                        broadcast(nickname + ": " + message);
                    }
                }
            } catch (IOException e) {
                shutdown();
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }

        public void shutdown() {
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

    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}
