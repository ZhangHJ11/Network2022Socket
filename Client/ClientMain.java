package Client;

import java.io.IOError;
import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        int port = 12345;
        String host = "127.0.0.1";

        NewClient client = new NewClient(port, host);
    }
}
