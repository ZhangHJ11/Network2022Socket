package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class Response {
    private Request request;
    private BufferedWriter toClient;
    private StringBuilder content = new StringBuilder();
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
    }

    private void createHeadInfo(int statusCode, String url) {
        // Status Line
        headInfo.append("HTTP/1.1").append(BLANK);
        headInfo.append(statusCode).append(BLANK); // HTTP Status Code, default 200
        switch (statusCode) {
            case 200:
                headInfo.append("OK").append(CRLF);
                break;
            case 404:
                headInfo.append("NOT FOUND").append(CRLF);
                break;
            case 505:
                headInfo.append("SEVER ERROR").append(CRLF);
                break;
        }

        // Head Line
        setContent(url);
        headInfo.append("Date:").append(new Date()).append(CRLF);
        headInfo.append("Sever:").append("LBX Sever/0.0.0;charset=GBK").append(CRLF);
        String contentType = MIMEList.getMIMEType(url);
        headInfo.append("Content-type:").append(contentType).append(CRLF);
        headInfo.append("Content-length:").append(contentLen).append(CRLF);
        headInfo.append(CRLF);

        // Body
        headInfo.append(content);
    }

    public Response appendContent(String info) {
        content.append(info);
        contentLen += info.getBytes().length;
        return this;
    }

    public void pushToClient(int statusCode) {
        createHeadInfo(statusCode, request.getURL());
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
                    new FileReader(System.getProperty("user.dir") + File.separator + "server" + File.separator + url));
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
