package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Date;

public class Response {
    private Request request;
    private BufferedWriter toClient;
    //private StringBuilder content = new StringBuilder();
    private String content;
    private StringBuilder headInfo = new StringBuilder();
    private int contentLen = 0; // bytes number;
    private final String BLANK = " ";
    private final String CRLF = "\r\n";
    MIMETypes MIMEList = MIMETypes.getMIMELists();

    public Response(Socket client, Request request) {
        try {
            this.request = request;
            toClient = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        content = Arrays.toString(request.fileData);
    }

    private void createHeadInfo(int statusCode, String location) {
        // Status Line
        headInfo.append("HTTP/1.1").append(BLANK);
        headInfo.append(statusCode).append(BLANK); // HTTP Status Code, default 200
        switch (statusCode) {
            case 200:
                headInfo.append("OK").append(CRLF);
                break;
            case 301:
                headInfo.append("Moved Permanently").append(CRLF);
                break;
            case 302:
                headInfo.append("Found").append(CRLF);
                break;
            case 304:
                headInfo.append("Not Modified").append(CRLF);
                break;
            case 404:
                headInfo.append("NOT FOUND").append(CRLF);
                break;
            case 405:
                headInfo.append("NOT Permitted").append(CRLF);
                break;
            case 500: //服务端错误
                headInfo.append("Internal Server Error").append(CRLF);
                break;
        }
        if(statusCode == 301){
            headInfo.append("Location: ").append(location);
        }
        // Head Line
        headInfo.append("Date:").append(new Date()).append(CRLF);
        headInfo.append("Server:").append("HOST Sever/0.0.0;charset=GBK").append(CRLF);
        //String ContentType = MIMEList.getMIMEType(location);
        headInfo.append("Content-type:").append("text/html").append(CRLF);
        headInfo.append("Content-length:").append(contentLen).append(CRLF);

        headInfo.append(CRLF);

        // Return content, blank now, need html file
        //setContent(request.getURL());
        //headInfo.append(content);
        // Body
//        headInfo.append(content);
    }

    public Response appendContent(String info) {
//        content.append(info);
        contentLen += info.getBytes().length;
        return this;
    }

    /**
     * 返回客户端
     * @param statusCode
     */
    public void pushToClient(int statusCode,String location) {
        createHeadInfo(request.statusCode, request.location);
        System.out.println(request.statusCode+" " + request.location);
        System.out.println(headInfo);
        try {
            toClient.write(headInfo.toString());
            toClient.write(content.toString());
            toClient.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Push Failed.");
        }
    }

    private void setContent(String url) {
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(System.getProperty("user.dir") + File.separator + url));
            String tmp;
            while ((tmp = reader.readLine()) != null) {
                appendContent(tmp);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
