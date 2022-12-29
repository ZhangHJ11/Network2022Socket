package Client.methods;

import Client.Connect;
import Client.RedirectList;
import Client.Requestmessage.HTTPRequest;
import Client.Requestmessage.RequestBody;
import Client.Requestmessage.RequestHead;
import Client.Requestmessage.RequestLine;
import server.MIMETypes;
import util.StreamReader;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class Put implements RequestMethod {

    private Connect connection;

    public Put(Connect connection) {
        this.connection = connection;
    }

    private HTTPRequest assembleRequest(String url) {
        RequestLine requestline = new RequestLine("PUT", url);
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
        requestHead.put("Content-type", MIMETypes.getMIMELists().getMIMEType(url));
        requestHead.put("Time", "10000");

        return new HTTPRequest(requestline, requestHead, null);
    }

    public void conductResponse(String url) throws IOException {
        String message = StreamReader.readAll(connection.getReceiveStream());
        String headline = message.substring(0, message.indexOf('\n') + 1);
        System.out.println(headline);
        String[] head = headline.split(" ");
        if (Objects.equals(head[1], "405")) {
            System.out.println("====>>>> WARNING <<<<====" + System.lineSeparator() + "This is not permitted by the server." + System.lineSeparator());
        }
    }

    public void sendRequest(String url, RequestBody body) throws IOException {
        HTTPRequest request = assembleRequest(url);
        connection.getSendStream().write(request.toString().getBytes());
        conductResponse(url);
    }
}
