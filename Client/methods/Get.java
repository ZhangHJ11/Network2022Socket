package Client.methods;

import Client.Connect;
import Client.Requestmessage.HTTPRequest;
import Client.Requestmessage.RequestBody;
import Client.Requestmessage.RequestHead;
import Client.Requestmessage.RequestLine;
import util.InputStreamReader;

import java.io.IOException;
import java.io.InputStream;

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
        String message = InputStreamReader.readAll(connection.getReceiveStream());
        String headline = message.substring(0, message.indexOf('\n')+1);
        message=message.substring(message.indexOf('\n')+1);
        String[] head = headline.split(" ");
        switch (head[1]) {
//            status code
            case "200":
                System.out.println(headline+message);
                break;
            case "301":
                String newLocation=message.substring(0, message.indexOf('\n')+1);
                newLocation=newLocation.substring(newLocation.indexOf(' ')+1);
                System.out.println("Redirecting to: "+newLocation);
                sendRequest("./"+newLocation,null);
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
