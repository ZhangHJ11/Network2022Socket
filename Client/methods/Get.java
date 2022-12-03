package Client.methods;

import Client.Connections;
import Client.Requestmessage.HTTPRequest;
import Client.Requestmessage.RequestHead;
import Client.Requestmessage.RequestLine;
import util.InputStreamReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Socket;

public class Get implements RequestMethod {
    private String host;
    private int port;
    private Connections pool;

    public Get(String host, int port, Connections pool) {
        this.host = host;
        this.port = port;
        this.pool = pool;
    }


    private HTTPRequest assembleRequest(String url,boolean isKeepAlive) {
        RequestLine requestline = new RequestLine("GET", url);
        RequestHead requestHead = new RequestHead();

        requestHead.put("Accept", "*/*");
        requestHead.put("Accept-Language", "zh-cn");
        requestHead.put("User-Agent", "WeDoRay-HTTPClient");

        if (port != 80 && port != 443) {
            requestHead.put("Host", host + ':' + port);
        } else {
            requestHead.put("Host", host); // 访问默认端口的时候是不需要端口号的
        }
        requestHead.put("Connection", isKeepAlive?"Keep-Alive":"");

        return new HTTPRequest(requestline, requestHead, null);
    }

    public void conductResponse(InputStream inputStream) throws IOException {
        System.out.println(InputStreamReader.readAll(inputStream));

    }

    public void sendRequest(String url, boolean isKeepAlive) throws IOException {
        //实现发送请求
        try(Socket server = new Socket(this.host, this.port)) {
            HTTPRequest request = assembleRequest(url,isKeepAlive);
            server.getOutputStream().write(request.toString().getBytes());

        }
        catch (ConnectException e) {
            e.printStackTrace();
        }
    }
}
