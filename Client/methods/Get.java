package Client.methods;

import Client.Connect;
import Client.RedirectList;
import Client.Requestmessage.HTTPRequest;
import Client.Requestmessage.RequestBody;
import Client.Requestmessage.RequestHead;
import Client.Requestmessage.RequestLine;
import util.FileMaker;
import util.StreamReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

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

    public void conductResponse(String url) throws IOException {
        String message = StreamReader.readAll(connection.getReceiveStream());
        String headline = message.substring(0, message.indexOf('\n')+1);
        message=message.substring(message.indexOf('\n')+1);
        String[] head = headline.split(" ");
        switch (head[1]) {
            case "200":
                int bodyIndex=message.indexOf("\r\n\r\n")+4;
                String fileName=url.substring(12);
                FileMaker.write("./Client/Cache/"+fileName,new ByteArrayInputStream(message.substring(bodyIndex).getBytes()));
                break;
            case "301": {
                String newLocation = message.substring(0, message.indexOf('\n') + 1);
                newLocation = "./"+newLocation.substring(newLocation.indexOf(' ') + 1,newLocation.indexOf("\r\n"));
                System.out.println("301 Redirecting to: " + newLocation);
                RedirectList.update(url,newLocation);
                sendRequest(newLocation, null);
                break;
            }
            case "302": {
                String newLocation = message.substring(0, message.indexOf('\n') + 1);
                newLocation = newLocation.substring(newLocation.indexOf(' ') + 1);
                System.out.println("302 Redirecting to: " + newLocation);
                sendRequest("./" + newLocation, null);
                break;
            }
            case "404":
                System.out.println("404 Not Found");
                break;
        }

    }

    public void sendRequest(String url, RequestBody body) throws IOException {

        for(Map.Entry<String,String>entry: RedirectList.getRedirectList().entrySet()) {
            if(entry.getKey().equals(url)) {
                url=entry.getValue();
                break;
            }
        }

        HTTPRequest request=assembleRequest(url);
        connection.getSendStream().write(request.toString().getBytes());
        conductResponse(url);

    }
}
