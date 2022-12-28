package server;

import util.FileTable;

import java.io.IOException;
import java.net.ServerSocket;

public class HTTPServer {

    public static String SERVER_ERROR_RES = "Resources/500.html"; // 凡是服务器错误都返回这个页面
    public static String NOT_FOUND_RES = "Resources/404.html"; // 404 页面
    public static String METHOD_NOT_ALLOWED_RES = "Resources/405.html"; // 405 页面
    public static String NOT_MODIFIED_RES = "Resources/304.html"; //
    private ServerSocket serverSocket; // get connect with chrome
    public static FileTable modifiedFileTable; // 记录文件修改时间，用于304

    /**
     * 初始化文件时间，用于304
     */
    public HTTPServer() {
        HTTPServer.modifiedFileTable = new FileTable();
        HTTPServer.modifiedFileTable.initInAFolder("./server/Resources");
    }

    // start sever
    public void start() {
        try {
            serverSocket = new ServerSocket(8888);
            receive();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Sever start failed.");
        }
    }

    // get connect
    public void receive() {
        ServerConnect serverConnect = new ServerConnect();
        serverConnect.creat(serverSocket);
        // serverConnect.setSoTimeout(10000);

        // 第一次链接，单独写出来是用来判断长链接的
        Request firstRequest = new Request(serverConnect.socket);
        // serverConnect.setSoTimeout(firstRequest.getTimeOut());
        Handle firstHandle = new Handle(firstRequest);
        firstHandle.handle();
        Response firstResponse = new Response(serverConnect.socket, firstRequest);
        firstResponse.pushToClient(firstHandle.statusCode);

        /** insert by liu */
        if (Handle.isR) {
            Request Request = new Request(serverConnect.socket);
            Response Response = new Response(serverConnect.socket, Request);
            Handle handle2 = new Handle(Request);
            handle2.handle();
            Response.pushToClient(handle2.statusCode);
        }

        // 长链接模式
        if (firstRequest.isKeepAlive()) {
            while (true) {
                Request Request = new Request(serverConnect.socket);
                // serverConnect.setSoTimeout(Request.getTimeOut());
                Request.getType();
                Response Response = new Response(serverConnect.socket, Request);
                Handle handle2 = new Handle(Request);
                handle2.handle();
                Response.pushToClient(handle2.statusCode);
                if (!Request.isKeepAlive()) {
                    serverConnect.close();
                    break;
                }
            }
        }
        serverConnect.close();
    }

    // 服务端入口
    public static void main(String[] args) {
        HTTPServer sever = new HTTPServer();
        sever.start();
    }
}