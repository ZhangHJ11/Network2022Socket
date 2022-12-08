package Client.methods;

import Client.Connect;
import Client.Connections;
import Client.Requestmessage.HTTPRequest;
import Client.Requestmessage.RequestBody;
import Client.Requestmessage.RequestHead;
import Client.Requestmessage.RequestLine;
import util.InputStreamReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Socket;

public class Post implements RequestMethod {
    private String host;
    private int port;
    private Connections pool;

    public Post(Connect connection) {
    }

    private HTTPRequest assembleRequest(String url,boolean isKeepAlive,RequestBody body){
        RequestLine requestline = new RequestLine("POST", url);
        RequestHead requestHead = new RequestHead();

        requestHead.put("Accept", "*/*");
        requestHead.put("Accept-Language", "zh-cn");
        requestHead.put("User-Agent", "Test-HTTPClient");
        if (port != 80 && port != 443) {
            requestHead.put("Host", host + ':' + port);
        } else {
            requestHead.put("Host", host); // 访问默认端口的时候是不需要端口号的
        }
        requestHead.put("Connection", isKeepAlive?"Keep-Alive":"");

        return new HTTPRequest(requestline, requestHead, body);
    }

    public void conductResponse(InputStream inputStream) throws IOException {
        //实现 处理响应 的操作  对状态码做出处理
        String res = InputStreamReader.readAll(inputStream);
        String headline = res.substring(0,res.indexOf('\n'));
        String[] head = headline.split(" ");
        switch (head[1]){
//            status code
            case "200":
                System.out.println(res);
                break;
            case "404":
                System.out.println("404 Not Found");
                break;
        }
    }

    public void sendRequest(String url, RequestBody body) throws IOException {
        //实现发送请求
        try(Socket server = new Socket(this.host, this.port)) {
            HTTPRequest request = assembleRequest(url,false,body);
            server.getOutputStream().write(request.toString().getBytes());

            InputStream in = server.getInputStream();
            conductResponse(in);
        }
        catch (ConnectException e) {
            e.printStackTrace();
        }
    }
}
