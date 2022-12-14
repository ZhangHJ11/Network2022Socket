package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ServerConnect {
    protected Socket socket;

    public void creat(ServerSocket serverSocket) {
        try {
            socket = serverSocket.accept();
            System.out.println("Got a client.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Close Error.");
        }
    }

    public void setSoTimeout(int time) {
        try {
            socket.setSoTimeout(time);
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("Time Out.");
        }
    }
}
