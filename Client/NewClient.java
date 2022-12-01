package Client;

import Client.Requestmessage.HttpRequest;
import Client.Requestmessage.Requestbody;
import Client.Requestmessage.Requesthead;
import Client.Requestmessage.Requestline;
import Client.methods.Get;
import Client.methods.Post;
import Client.methods.RequestMethod;

import java.io.IOException;
import java.net.Socket;

public class NewClient {
    private String host;
    private int port;
    private static Connections pool = new Connections();
    RequestMethod requestMethod;


    public NewClient(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void Get(String url) throws IOException {
        Socket socket = new Socket(this.host, this.port);

        HttpRequest httpRequest = assembleRequest(url);

        socket.close();
    }

    public HttpRequest assembleRequest(String url) throws IOException {
        Requestline requestline = new Requestline("GET", url);
        Requesthead requesthead = new Requesthead();
        Requestbody requestbody = null;

        requesthead.put("Accept", "*/*");
        requesthead.put("Accept-Language", "zh-cn");
        requesthead.put("User-Agent", "WeDoRay-HTTPClient");

        if (port != 80 && port != 443) {
            requesthead.put("Host", host + ':' + port);
        } else {
            requesthead.put("Host", host); // 访问默认端口的时候是不需要端口号的
        }
        requesthead.put("Connection", "Keep-Alive");

        return new HttpRequest(requestline, requesthead, requestbody);
    }

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
}
