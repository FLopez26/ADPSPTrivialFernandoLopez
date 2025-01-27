package server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerClient extends Thread{
    private int port = 48120;

    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(port)) {
            while (true) {
                ClientGame clientGame = new ClientGame(server.accept());
                clientGame.start();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
