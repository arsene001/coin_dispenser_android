package com.app.coindispenser.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerHandler {

    private String host = "192.168.1.47";
    private int port = 8080;
    private Socket serverSocket;
    private PrintWriter writer;
    private BufferedReader reader;

    void startConnection() throws IOException {
        serverSocket = new Socket(host, port);
        writer = new PrintWriter(serverSocket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
    }

    void stopConnection() throws IOException {
        reader.close();
        writer.close();
        serverSocket.close();
    }

    boolean loginUserIn(String message) {
        try {
            writer.println(message);
            String resp = reader.readLine();
            return resp.equals("true");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    String calculateDenomination(String message) {

        try {
            writer.println(message);
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
