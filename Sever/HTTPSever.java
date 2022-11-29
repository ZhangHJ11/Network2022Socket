import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPSever {

    private ServerSocket serverSocket; // get connect with chrome

    // start sever
    public void start() {
        try {
            serverSocket = new ServerSocket(8888);
            receive();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Sever start failed.");
        }
    }

    // get connect
    public void receive() {
        try {
            Socket client = serverSocket.accept();
            System.out.println("Got a client.");

            // GET message
            Requset requset = new Requset(client);

            // POST message
            Response response = new Response(client);
            response.pushToClient(200);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Client error.");
        }
    }

    // stop sever
    public void stop() {

    }

    public static void main(String[] args) {
        HTTPSever sever = new HTTPSever();
        sever.start();
    }

    private static class ClientHandler extends Thread {
        private Socket socket;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }
    }
}