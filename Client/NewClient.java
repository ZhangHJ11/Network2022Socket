package Client;

import Client.Requestmessage.RequestBody;
import Client.methods.Get;
import Client.methods.Post;
import Client.methods.RequestMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class NewClient {
    private String host;
    private int port;
    private final Connect connection;
    RequestMethod requestMethod;


    public NewClient(int port, String host, boolean isKeepAlive) throws IOException {
        connection = new Connect(host, port, isKeepAlive);
        connection.creat();


        BufferedReader bufferedReader = new BufferedReader(new java.io.InputStreamReader(System.in));
        do {
            String cmd = bufferedReader.readLine();
            System.out.println(cmd);
            if (Objects.equals("stop", cmd)) {
                switchMode("Get");
                requestMethod.sendRequest(host, new RequestBody());
                connection.setKeepAlive(false);
            } else if (Objects.equals("Post", cmd)) {
                String url = cmd.substring(3);
                switchMode("POST");
                requestMethod.sendRequest(url, null);
            } else if (Objects.equals("get", cmd.substring(0, 3))) {
                String url = cmd.substring(4);
                switchMode("GET");
                requestMethod.sendRequest(url, null);
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

//    public void Login(String input,boolean isKeepAlive) throws IOException{
//        byte[] Bytes=input.getBytes();
//        RequestBody body=new RequestBody(Bytes);
//        try {
//            requestMethod.sendRequest("/registerOrLogin",isKeepAlive, body);//调用POST请求发送登录注册的请求
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
