package server;

import util.FileTable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer {

    public static String SERVER_ERROR_RES = "Resources/500.html"; // 凡是服务器错误都返回这个页面
    public static String NOT_FOUND_RES = "Resources/404.html"; // 404 页面
    public static String METHOD_NOT_ALLOWED_RES = "Resources/405.html"; // 405 页面
    public static String NOT_MODIFIED_RES = "Resources/304.html"; //
    public static String POST_SUCCESS_RES = "Resources/post_success.html";
    public static int DEFAULT_PORT = 8888;
    public static String HOSTNAME = "127.0.0.1";
    private ServerSocket serverSocket; // get connect with chrome
    public static FileTable modifiedFileTable; // 记录文件修改时间，用于304

    /*
     * public HTTPServer(){
     * HTTPServer.modifiedFileTable = new FileTable();
     * HTTPServer.modifiedFileTable.initInAFolder(BIND_DIR);
     * }
     */
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
        try {
            Socket client = serverSocket.accept();
            System.out.println("Got a client.");
            // GET message
            Request firstRequest = new Request(client);
            Handle firstHandle = new Handle(firstRequest);
            firstHandle.handle();
            Response firstResponse = new Response(client, firstRequest);
            firstResponse.pushToClient(firstHandle.statusCode);

            /** insert by liu */
            if(Handle.isR){
                Request Request = new Request(client);
                Response Response = new Response(client, Request);
                Handle handle2 = new Handle(Request);
                handle2.handle();
                Response.pushToClient(handle2.statusCode);
            }

            if (firstRequest.isKeepAlive()) {
                while (true) {
                    Request Request = new Request(client);
                    Response Response = new Response(client, Request);
                    Handle handle2 = new Handle(Request);
                    handle2.handle();
                    Response.pushToClient(handle2.statusCode);
                    if(!Request.isKeepAlive()){
                        client.close();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Client error.");
        }
    }

    // stop sever
    public void stop() {

    }

    public static void main(String[] args) {
        HTTPServer sever = new HTTPServer();
        sever.start();
    }

    private static class ClientHandler extends Thread {
        private Socket socket;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }
    }
}