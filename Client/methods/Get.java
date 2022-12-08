package Client.methods;

import Client.Connect;
import Client.Connections;
import Client.Requestmessage.HTTPRequest;
import Client.Requestmessage.RequestBody;
import Client.Requestmessage.RequestHead;
import Client.Requestmessage.RequestLine;
import util.InputStreamReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Objects;

public class Get implements RequestMethod {
    Connect connection;

    public Get(Connect connection) {
        this.connection=connection;
    }


    private HTTPRequest assembleRequest(String url) {
        RequestLine requestline = new RequestLine("GET", url);
        RequestHead requestHead = new RequestHead();

        requestHead.put("Accept", "*/*");
        requestHead.put("Accept-Language", "zh-cn");
        requestHead.put("User-Agent", "Test-HTTPClient");
        if (connection.getPort() != 80 && connection.getPort() != 443) {
            requestHead.put("Host", connection.getHost() + ':' + connection.getPort());
        } else {
            requestHead.put("Host", connection.getHost()); // 访问默认端口的时候是不需要端口号的
        }
        requestHead.put("Connection", connection.isPersistent() ? "Keep-Alive" : "");

        return new HTTPRequest(requestline, requestHead, null);
    }

    public void conductResponse(InputStream inputStream) throws IOException {
        System.out.println(InputStreamReader.readAll(inputStream));
        String res = InputStreamReader.readAll(inputStream);
        String headline = res.substring(0, res.indexOf('\n'));
        String[] head = headline.split(" ");
        switch (head[1]) {
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

        HTTPRequest request=assembleRequest(url);
        connection.getSendStream().write(request.toString().getBytes());
        conductResponse(connection.getReceiveStream());

    }
}
