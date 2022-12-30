package Client.methods;

import Client.Connect;
import Client.RedirectList;
import Client.Requestmessage.HTTPRequest;
import Client.Requestmessage.RequestBody;
import Client.Requestmessage.RequestHead;
import Client.Requestmessage.RequestLine;
import server.MIMETypes;
import util.FileMaker;
import util.StreamReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

public class Get implements RequestMethod {
    Connect connection;

    public Get(Connect connection) {
        this.connection = connection;
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

    public void conductResponse(String url) throws IOException {
        //以byte[]形式读取服务端返回内容，以实现MIME中的多种文件格式的传输
        byte[] data = StreamReader.getBytes(connection.getReceiveStream());
        char[] tmp = new char[data.length];
        for (int i = 0; i < data.length; i++)
            tmp[i] = (char) data[i];
        //获取报文头String
        String message = String.valueOf(tmp);
        String headline = message.substring(0, message.indexOf('\n') + 1);
        String getHead = message.substring(message.indexOf('\n') + 1);
        String[] head = headline.split(" ");
        int bodyIndex = message.indexOf("\n\r\n") + 3;
        System.out.println(message.substring(0,bodyIndex-3));
        switch (head[1]) {
            //status code
            case "200":
                //OK
                String fileName = url.substring(12);
                byte[] fileContent = new byte[data.length - bodyIndex];
                System.arraycopy(data, bodyIndex, fileContent, 0, fileContent.length);
                FileMaker.write("./Client/Cache/" + fileName, new ByteArrayInputStream(fileContent));
                break;
            case "301": {
                //301 permanent redirect
                String newLocation = getHead.substring(0, getHead.indexOf('\n') + 1);
                newLocation = "./" + newLocation.substring(newLocation.indexOf(' ') + 1, newLocation.indexOf("\n"));
                System.out.println("301 Redirecting to: " + newLocation);
                RedirectList.update(url, newLocation);
                sendRequest(newLocation, null);
                break;
            }
            case "302": {
                //302 temporary redirect
                String newLocation = getHead.substring(0, getHead.indexOf('\n') + 1);
                newLocation = newLocation.substring(newLocation.indexOf(' ') + 1, newLocation.indexOf("\n"));
                System.out.println("302 Redirecting to: " + newLocation);
                sendRequest("./" + newLocation, null);
                break;
            }
            case "304": {
                //304 in Cache
                System.out.println("304 Redirecting to: " + url);
                break;
            }
            case "404":
                System.out.println("404 Not Found");
                break;
            case "405":
                System.out.println("Method Not Allowed");
                break;
            case "500":
                System.out.println("Internal Server Error");
                break;
        }

    }

    public void sendRequest(String url, RequestBody body) throws IOException {

        //发送报文前先查找重定向表
        for (Map.Entry<String, String> entry : RedirectList.getRedirectList().entrySet()) {
            if (entry.getKey().equals(url)) {
                url = entry.getValue();
                break;
            }
        }

        HTTPRequest request = assembleRequest(url);
        connection.getSendStream().write(request.toString().getBytes());
        conductResponse(url);

    }
}
