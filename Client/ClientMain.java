package Client;

import Client.Loginservice.Login;
import Test.Gettest;

import java.io.IOException;
import java.util.HashMap;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        int port = 8888;
        String host = "127.0.0.1";
        Gettest gettest = new Gettest();
        gettest.PersistentGet(port, host);
        // NewClient client=new NewClient(port,host,"POST");
        // //进行注册
        // Login login=new Login();
        // String input=login.login();//进行注册
        // client.Login(input,true);

    }
}
