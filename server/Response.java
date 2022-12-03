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
    private BufferedWriter toClient;
    private StringBuilder content = new StringBuilder();
    private StringBuilder headInfo = new StringBuilder();
    private int contentLen = 0; // bytes number;
    private final String BLANK = " ";
    private final String CRLF = "\r\n";
    // MIMETypes MIMEList = MIMETypes.getMIMELists();

    public Response(Socket client) {
        try {
            toClient = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createHeadInfo(int statusCode, String location) {
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
        headInfo.append("Date:").append(new Date()).append(CRLF);
        headInfo.append("Sever:").append("LBX Sever/0.0.0;charset=GBK").append(CRLF);
        // String ContentType = MIMEList.getMIMEType(location);
        headInfo.append("Content-type:").append("text/html").append(CRLF);
        headInfo.append("Content-length:").append(contentLen).append(CRLF);
        headInfo.append(CRLF);
        // Return content, blank now, need html file
        headInfo.append(content);
    }

    public Response appendContent(String info) {
        content.append(info);
        contentLen += info.getBytes().length;
        return this;
    }

    public void pushToClient(int statusCode) {
        createHeadInfo(statusCode, "");
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
            BufferedReader reader = new BufferedReader(new FileReader(url));
            String tmp;
            while ((tmp = reader.readLine()) != null) {
                appendContent(tmp);
            }
            appendContent(tmp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
