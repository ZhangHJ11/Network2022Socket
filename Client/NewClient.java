package Client;

import Client.Loginservice.Login;
import Client.Requestmessage.RequestBody;
import Client.methods.Get;
import Client.methods.Post;
import Client.methods.RequestMethod;
import util.StreamReader;

import java.io.IOException;

public class NewClient {
    private final Connect connection;
    RequestMethod requestMethod;


    public NewClient(int port, String host, boolean isKeepAlive) throws IOException {
        connection = new Connect(host, port, isKeepAlive);
        connection.creat();
//        login message
        Login login = new Login();
        String infor = login.set();

        do {
            String cmd = StreamReader.readline();
            while (!cmd.contains(" ")) {
//                invalid input
                System.out.println("This is an invalid input.");
                cmd = StreamReader.readline();
            }
            int index = cmd.indexOf(' ');
            String url = cmd.substring(index + 1);
            switch (cmd.substring(0, index)) {
                case "stop":
//                  stop ./Resources/index.html
                    switchMode("Get");
                    connection.setKeepAlive(false);
                    requestMethod.sendRequest(url, new RequestBody());
                    System.out.println("====>>>> MESSAGE LINE <<<<===");
                    System.out.println("connection released!!!");
                    continue;
                case "get":
//                  get ./Resources/index.html
//                  get ./Resources/2.png
                    System.out.println("====>>>> RECEIVING MESSAGE <<<<===");
                    switchMode("GET");
                    requestMethod.sendRequest(url, null);
                    continue;
                case "post":
//                  post ./Resources/index.html url格式不对
                    switchMode("POST");
                    requestMethod.sendRequest(url, new RequestBody(infor));
                    continue;
                default:
                    System.out.println("This is an invalid input.");
                    System.out.println("please input again");
            }
        } while (connection.isPersistent());

    }

    public void switchMode(String method) {
        if (method.equals("GET")) {
            this.requestMethod = new Get(connection);
        } else if (method.equals("POST")) {
            this.requestMethod = new Post(connection);
        }
    }

}
