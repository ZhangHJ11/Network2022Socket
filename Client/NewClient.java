package Client;

import Client.Requestmessage.RequestBody;
import Client.methods.Get;
import Client.methods.Post;
import Client.methods.RequestMethod;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class NewClient {
    private String host;
    private int port;
    private static Connections pool = new Connections();
    RequestMethod requestMethod;


    public NewClient(int port, String host, String method) {
        this.port = port;
        this.host = host;
        if (method.equals("Get")) {
            this.requestMethod = new Get(host, port, pool);
        } else if (method.equals("Post")) {
            this.requestMethod = new Post(host, port, pool);
        }
    }

    public void switchMode(String method) {
        if (method.equals("GET")) {
            this.requestMethod = new Get(host, port, pool);
        } else if (method.equals("POST")) {
            this.requestMethod = new Post(host, port, pool);
        }
    }

    public void Get(String host,boolean isKeepAlive) throws IOException {
        requestMethod.sendRequest(host,isKeepAlive,new RequestBody());
    }
    public void Login(String input,boolean isKeepAlive) throws IOException{
        byte[] Bytes=input.getBytes();
        RequestBody body=new RequestBody(Bytes);
        try {
            requestMethod.sendRequest("/registerOrLogin",isKeepAlive, body);//调用POST请求发送登录注册的请求
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
