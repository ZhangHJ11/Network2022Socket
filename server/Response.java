package server;

import util.GetFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class Response {
    private Request request;
    private BufferedWriter toClient;
    private String content;
    /**test*/
    private static String OldContent;
    private StringBuilder headInfo = new StringBuilder();
    private int contentLen = 0; // bytes number;
    private final String BLANK = " ";
    private final String CRLF = "\r\n";
    MIMETypes MIMEList = MIMETypes.getMIMELists();

    /**just for test*/
    private static int OldStatusNode = 0;
    public Response(Socket client, Request request) {
        try {
            this.request = request;
            toClient = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createHeadInfo(int statusCode) {
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
            case 500: // 服务端错误
                headInfo.append("Internal Server Error").append(CRLF);
                break;
            default: break;
        }
        if (statusCode == 301) {
            headInfo.append("Location: ").append(request.getURL()).append(CRLF);
        }
        /**test*/
        if(301 != OldStatusNode && 302 != OldStatusNode) {
            try {
                this.content = GetFile.getFile(request.getURL());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            this.content = OldContent;
        }
        this.contentLen = content.getBytes().length;
        headInfo.append("Date:").append(new Date()).append(CRLF);
        headInfo.append("Server:").append("HOST Sever/0.0.0;charset=GBK").append(CRLF);
        String ContentType = MIMEList.getMIMEType(request.getURL());
        headInfo.append("Content-type:").append(ContentType).append(CRLF);
        headInfo.append("Content-length:").append(contentLen).append(CRLF);

        headInfo.append(CRLF);

        /**test*/
        OldStatusNode = statusCode;
        OldContent = content;
        // Body
        // headInfo.append(content);
    }

    /**
     * 返回客户端
     * 
     * @param statusCode
     */
    public void pushToClient(int statusCode) {
        createHeadInfo(statusCode);
        System.out.println(headInfo);
        //System.out.println(content);
        try {
            toClient.write(headInfo.toString());
            toClient.write(content.toString());
            toClient.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Push Failed.");
        }
    }

}
