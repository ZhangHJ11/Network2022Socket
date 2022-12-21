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

public class Post implements RequestMethod {
    private Connect connection;

    public Post(Connect connection) {
        this.connection = connection;
    }

    private HTTPRequest assembleRequest(String url, RequestBody body) {
        RequestLine requestline = new RequestLine("POST", url);
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

        return new HTTPRequest(requestline, requestHead, body);
    }

    public void conductResponse(String url) throws IOException {
        //实现 处理响应 的操作  对状态码做出处理
        String message = StreamReader.readAll(connection.getReceiveStream());
        String headline = message.substring(0, message.indexOf('\n') + 1);
        message = message.substring(message.indexOf('\n') + 1);
        String[] head = headline.split(" ");
        switch (head[1]) {
//            status code
            case "200":
                System.out.println(message);
                break;
            case "301": {
//                永久重定向
                String newLocation = message.substring(0, message.indexOf('\n') + 1);
                newLocation = "./" + newLocation.substring(newLocation.indexOf(' ') + 1, newLocation.indexOf("\r\n"));
                System.out.println("301 Permanent Redirecting To: " + newLocation);
                RedirectList.update(url, newLocation);
//                sendRequest(newLocation, null);
                break;
            }
            case "302": {
//                临时重定向
                String newLocation = message.substring(0, message.indexOf('\n') + 1);
                newLocation = "./" + newLocation.substring(newLocation.indexOf(' ') + 1);
                System.out.println("302 Temporary Redirecting To: " + newLocation);
//                sendRequest("./" + newLocation, null);
                break;
            }
            case "404":
                System.out.println("404 Not Found");
                break;
        }
    }

    public void sendRequest(String url, RequestBody body) throws IOException {
        for (Map.Entry<String, String> entry : RedirectList.getRedirectList().entrySet()) {
            if (entry.getKey().equals(url)) {
                url = entry.getValue();
                break;
            }
        }

        HTTPRequest request = assembleRequest(url, body);
        byte[] bytes = new byte[request.toString().getBytes().length + request.requestBody.getlength()];
//        head
        System.arraycopy(request.toString().getBytes(), 0, bytes, 0,
                request.toString().getBytes().length);
//        body
        System.arraycopy(request.bodytobyte(), 0, bytes, request.toString().getBytes().length,
                request.bodytobyte().length);
//        send
        connection.getSendStream().write(bytes);
        conductResponse(url);

    }
}
