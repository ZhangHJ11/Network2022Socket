package Client;

import Test.Gettest;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        int port = 8888;
        String host = "127.0.0.1";
        Gettest gettest = new Gettest();
        gettest.NonPersistentGet(port, host);
        // Login login = new Login();
        // login.login();
    }
}
