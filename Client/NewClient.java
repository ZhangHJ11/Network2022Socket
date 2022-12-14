package Client;

import Client.Loginservice.Login;
import Client.Requestmessage.RequestBody;
import Client.methods.Get;
import Client.methods.Post;
import Client.methods.RequestMethod;
import util.GetFile;
import util.StreamReader;

import java.io.BufferedReader;
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
//        register after login
        switchMode("POST");
        requestMethod.sendRequest("./Resources/index.html", new RequestBody(infor));

        do {
            System.out.println("get, post or stop?");
            String cmd = StreamReader.readline();
            while ((!cmd.startsWith("get") && !cmd.startsWith("post") && !cmd.startsWith("stop"))) {
//                invalid input
                System.out.println("====>>>> WARNING <<<<===");
                System.out.println("This is an invalid input." + System.lineSeparator() + "Please input again.");
                cmd = StreamReader.readline();
            }
            switch (cmd) {
                case "stop":
                    switchMode("Get");
                    connection.setKeepAlive(false);
                    requestMethod.sendRequest("./Resources/index.html", new RequestBody());
                    System.out.println("====>>>> MESSAGE LINE <<<<===");
                    System.out.println("connection released!!!");
                    break;
                case "get":
//                  ./Resources/index.html
//                  ./Resources/2.png
//                        TODO:文件下载
                    switchMode("GET");
                    System.out.println("url?");
                    String url = StreamReader.readline();
                    System.out.println("====>>>> RECEIVING MESSAGE <<<<===");
                    requestMethod.sendRequest(url, null);
                    continue;
                case "post":
//                  post login or upload url
                    switchMode("POST");
                    System.out.println("login or upload?");
                    String type = StreamReader.readline();
                    while ((!type.startsWith("login") && !type.startsWith("upload"))) {
                        System.out.println("====>>>> WARNING <<<<===");
                        System.out.println("This is an invalid input." + System.lineSeparator() + "Please input again.");
                        type = StreamReader.readline();
                    }
                    if(type.equals("login")){
                        infor = login.change();
                        requestMethod.sendRequest("./Resources/index.html", new RequestBody(infor));
                    } else if (type.equals("upload")) {
//                        TODO:文件上传
                        System.out.println("the url of the file you want to upload?");
                        String fileurl = StreamReader.readline();
                        System.out.println("target url?");
                        url=StreamReader.readline();
                        requestMethod.sendRequest(url, new RequestBody("&type=picture"+GetFile.getFile(fileurl)));
                    }
                    continue;
                default:
                    System.out.println("====>>>> WARNING <<<<===");
                    System.out.println("This is an invalid input." + System.lineSeparator() + "Please input again.");
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
