package Client;

import Client.Loginservice.Login;
import Client.Requestmessage.RequestBody;
import Client.methods.Get;
import Client.methods.Post;
import Client.methods.RequestMethod;
import util.StreamReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class NewClient {
    private final Connect connection;
    RequestMethod requestMethod;

    public NewClient(int port, String host, boolean isKeepAlive) throws IOException {
        //        login in(temporarily true)
        connection = new Connect(host, port, true);
        connection.creat();
        // login message
        Login login = new Login();
        String infor = login.set();
        // register after login
        switchMode("POST");
        requestMethod.sendRequest("./Resources/index.html", new RequestBody(infor));
        connection.setKeepAlive(isKeepAlive);

        do {
            System.out.println("get, post or stop?");
            String cmd = StreamReader.readline();
            while ((!cmd.startsWith("get") && !cmd.startsWith("post") && !cmd.startsWith("stop"))) {
                // invalid input
                System.out.println("====>>>> WARNING <<<<====");
                System.out.println("This is an invalid input." + System.lineSeparator() + "Please input again.");
                cmd = StreamReader.readline();
            }
            switch (cmd) {
                case "stop":
                    switchMode("GET");
                    connection.setKeepAlive(false);
                    requestMethod.sendRequest("./Resources/index.html", new RequestBody());
                    System.out.println("====>>>> MESSAGE LINE <<<<====");
                    System.out.println("connection released!!!");
                    break;
                case "get":
                    // ./Resources/index.html
                    // ./Resources/4.png
                    switchMode("GET");
                    System.out.println("url?(e.g. ./Resources/index.html)");
                    String url = StreamReader.readline();
                    System.out.println("====>>>> RECEIVING MESSAGE <<<<====");
                    requestMethod.sendRequest(url, null);
                    continue;
                case "post":
                    // post login or upload url
                    switchMode("POST");
                    System.out.println("login or upload?");
                    String type = StreamReader.readline();
                    while ((!type.startsWith("login") && !type.startsWith("upload"))) {
                        System.out.println("====>>>> WARNING <<<<====");
                        System.out
                                .println("This is an invalid input." + System.lineSeparator() + "Please input again.");
                        type = StreamReader.readline();
                    }
                    if (type.equals("login")) {
//                        重新登陆
                        infor = login.change();
                        requestMethod.sendRequest("./Resources/index.html", new RequestBody(infor));
                    } else if (type.equals("upload")) {
//                        上传文件
                        System.out.println("the url of the file you want to upload?(e.g. uploadSuccess.html)");
                        String fileurl = StreamReader.readline();
                        File file = new File(System.getProperty("user.dir") + File.separator +
                                "Client" + File.separator + "Resources/" + fileurl);
                        System.out.println("target url?(e.g. ./Resources/uploadSuccess.html)");
                        url = StreamReader.readline();
                        while(!url.startsWith("./Resources")){
                            System.out.println("====>>>> WARNING <<<<====");
                            System.out.println("This is an invalid input." + System.lineSeparator() + "target url should starts with './Resources'.");
                            url = StreamReader.readline();
                        }
                        requestMethod.sendRequest(url, new RequestBody(Files.newInputStream(file.toPath())));
                    }
                    continue;
                default:
                    System.out.println("====>>>> WARNING <<<<====");
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
