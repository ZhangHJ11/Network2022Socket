package Client.methods;

import Client.Connect;
import Client.Requestmessage.HTTPRequest;
import Client.Requestmessage.RequestBody;
import Client.Requestmessage.RequestHead;
import Client.Requestmessage.RequestLine;
import util.InputStreamReader;

import java.io.IOException;

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

    public void conductResponse() throws IOException {
        String res = InputStreamReader.readAll(connection.getReceiveStream());
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
        conductResponse();

    }
}
