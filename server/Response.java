package server;

import util.GetFile;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

public class Response {
    private Request request;
    private OutputStream toClient;
    private byte[] content;
    private String contentType;
    private static byte[] OldContent;
    private StringBuilder headInfo = new StringBuilder();
    private int contentLen = 0; // bytes number;
    private final String BLANK = " ";
    private final String CRLF = "\r\n";
    MIMETypes MIMEList = MIMETypes.getMIMELists();
    private static int OldStatusNode = 0;

    public Response(Socket client, Request request) {
        try {
            this.request = request;
            toClient = client.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // create HeadInfo and get body
    private void createHeadInfo(int statusCode) {
        // Status Line
        headInfo.append("HTTP/1.1").append(BLANK);
        headInfo.append(statusCode).append(BLANK); // HTTP Status Code, default 200
        switch (statusCode) {
            case 200:
                headInfo.append("OK").append('\n');
                break;
            case 301:
                headInfo.append("Moved Permanently").append('\n');
                break;
            case 302:
                headInfo.append("Found").append('\n');
                break;
            case 304:
                headInfo.append("Not Modified").append('\n');
                break;
            case 404:
                headInfo.append("NOT FOUND").append('\n');
                break;
            case 405:
                headInfo.append("NOT Permitted").append('\n');
                break;
            case 500: // 服务端错误
                headInfo.append("Internal Server Error").append('\n');
                break;
            default:
                break;
        }
        // 重定向多一行
        if (statusCode == 301 || statusCode == 302) {
            headInfo.append("Location: ").append(request.getURL()).append('\n');
        }
        if (301 != OldStatusNode && 302 != OldStatusNode) {
            try {
                this.content = GetFile.getFile(request.getURL());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.content = OldContent;
        }
        this.contentLen = content.length;
        headInfo.append("Date:").append(new Date()).append('\n');
        headInfo.append("Server:").append("HOST Sever/0.0.0;charset=GBK").append('\n');
        contentType = MIMEList.getMIMEType(request.getURL());
        headInfo.append("Content-type:").append(contentType).append('\n');
        headInfo.append("Content-length:").append(contentLen).append('\n');

        headInfo.append(CRLF);

        OldStatusNode = statusCode;
        OldContent = content;
    }

    // 返回客户端
    public void pushToClient(int statusCode) {
        createHeadInfo(statusCode);
        System.out.println(headInfo);
        // System.out.println(content);
        try {
            char[] tmpC = headInfo.toString().toCharArray();
            byte[] tmp = new byte[tmpC.length];
            for (int i = 0; i < tmpC.length; i++) {
                tmp[i] = (byte) tmpC[i];
            }
            if (request.method.equals("GET")) {
                byte[] message = new byte[tmp.length + content.length];
                System.arraycopy(tmp, 0, message, 0, tmp.length);
                System.arraycopy(content, 0, message, tmp.length, content.length);
                toClient.write(message);
            } else {
                if (contentType.indexOf("text/") != -1) {
                    byte[] message = new byte[tmp.length + content.length];
                    System.arraycopy(tmp, 0, message, 0, tmp.length);
                    System.arraycopy(content, 0, message, tmp.length, content.length);
                    toClient.write(message);
                } else {
                    toClient.write(tmp);
                }
            }
            toClient.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Push Failed.");
        }
    }
}
