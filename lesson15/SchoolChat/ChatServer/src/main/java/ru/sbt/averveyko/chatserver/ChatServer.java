package ru.sbt.averveyko.chatserver;

import ru.sbt.averveyko.api.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    public static Set<String> users = new CopyOnWriteArraySet<>();
    public static MessageList messageList = new MessageList();

    public static void main(String[] args) {
        System.out.println("Starting chat server at port " + Connection.PORT);

        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(Connection.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExecutorService executorService = Executors.newFixedThreadPool(Connection.CONNECTION_CAPACITY);

        while (true) {
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            executorService.submit(new ServerChatWorker(clientSocket));
        }

    }
}
