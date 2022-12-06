package Client;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        int port = 8888;
        String host = "127.0.0.1";
        NewClient client = new NewClient(port, host, "Get");
        client.Get("." + "/" + "Resources" + "/" + "index.html", true);
    }
}
