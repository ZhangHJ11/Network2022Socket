import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketClient {
    public static void main(String[] args) {
        String hostname;
        int port;
        if (args.length > 2) {
            hostname = args[0];
            port = Integer.parseInt(args[1]);
        } else {
            hostname = "127.0.0.1";
            port = 9999;
        }

        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
//            create a local client
            System.out.println("hostname: " + hostname + " " + "postnum: " + port);
            Socket client = new Socket(hostname, port);

//            字节流转化为字符流，放入字符流缓存中
            reader = new BufferedReader(
                    new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
            writer = new PrintWriter(
                    client.getOutputStream(), true);

//            close the client
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
