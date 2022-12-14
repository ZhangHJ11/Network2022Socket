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
        requestHead.put("Content-type", MIMETypes.getMIMELists().getMIMEType(url));
        requestHead.put("Time", "10000");

        return new HTTPRequest(requestline, requestHead, null);
    }

    public void conductResponse() throws IOException {
        String message = StreamReader.readAll(connection.getReceiveStream());
        String headline = message.substring(0, message.indexOf('\n')+1);
        message=message.substring(message.indexOf('\n')+1);
        String[] head = headline.split(" ");
        if(head[1].equals("200")) {
            System.out.println(headline+message);
        }
        else if(head[1].equals("301")) {
            String newLocation=message.substring(0, message.indexOf('\n')+1);
            newLocation=newLocation.substring(newLocation.indexOf(' ')+1);
            System.out.println("301 Redirecting to: "+newLocation);
            sendRequest("./"+newLocation,null);
        }
        else if(head[1].equals("302")) {
            String newLocation=message.substring(0, message.indexOf('\n')+1);
            newLocation=newLocation.substring(newLocation.indexOf(' ')+1);
            System.out.println("302 Redirecting to: "+newLocation);
            sendRequest("./"+newLocation,null);
        }
        else if(head[1].equals("404")) {
            System.out.println("404 Not Found");
        }

    }

    public void sendRequest(String url, RequestBody body) throws IOException {

//        for(Map.Entry<String,String>entry: RedirectList.getRedirectList().entrySet()) {
//            if(entry.getKey().equals(url)) {
//                url=entry.getValue();
//                break;
//            }
//        }

        HTTPRequest request=assembleRequest(url);
        connection.getSendStream().write(request.toString().getBytes());
        conductResponse();

    }
}
