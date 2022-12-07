package Client.methods;

import Client.Connections;
import Client.Requestmessage.HTTPRequest;
import Client.Requestmessage.RequestHead;
import Client.Requestmessage.RequestLine;
import util.InputStreamReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Objects;

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
        requestHead.put("User-Agent", "Test-HTTPClient");
        if (port != 80 && port != 443) {
            requestHead.put("Host", host + ':' + port);
        } else {
            requestHead.put("Host", host); // 访问默认端口的时候是不需要端口号的
        }
        requestHead.put("Connection", isKeepAlive?"Keep-Alive":"");

        return new HTTPRequest(requestline, requestHead, null);
    }

    public void conductResponse(InputStream inputStream) throws IOException {
//        System.out.println(InputStreamReader.readAll(inputStream));
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

    public void sendRequest(String url, boolean isKeepAlive) throws IOException {
        //实现发送请求
        try(Socket server = new Socket(this.host, this.port)) {
            if (!isKeepAlive){
                HTTPRequest request = assembleRequest(url,isKeepAlive);
                server.getOutputStream().write(request.toString().getBytes());

                InputStream in = server.getInputStream();
                conductResponse(in);
            }else {
                BufferedReader bufferedReader = new BufferedReader(new java.io.InputStreamReader(System.in));
                while(true){
                    String cmd = bufferedReader.readLine();
                    if(Objects.equals(cmd, "stop")){
//                        send a release message
                        HTTPRequest request = assembleRequest(url,false);
                        server.getOutputStream().write(request.toString().getBytes());
                        InputStream in = server.getInputStream();
                        conductResponse(in);
                        break;
                    }else {
                        HTTPRequest request = assembleRequest(url,isKeepAlive);
                        server.getOutputStream().write(request.toString().getBytes());

                        InputStream in = server.getInputStream();
                        conductResponse(in);
                    }
                }
            }

        }
        catch (ConnectException e) {
            e.printStackTrace();
        }
    }
}
