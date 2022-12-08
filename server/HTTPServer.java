package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer {

    public static String BIND_DIR = "server/server.Resources";// 资源目录
    public static String SERVER_ERROR_RES = "/500.html"; // 凡是服务器错误都返回这个页面
    public static String NOT_FOUND_RES = "/404.html"; // 404 页面
    public static String METHOD_NOT_ALLOWED_RES = "/405.html"; //405 页面
    public static String NOT_MODIFIED_RES = "/304.html"; //405 页面
    public static String POST_SUCCESS_RES = "/post_success.html";
    public static int DEFAULT_PORT = 8888;
    public static String HOSTNAME = "127.0.0.1";
    private ServerSocket serverSocket; // get connect with chrome
    public static FileTable modifiedFileTable; // 记录文件修改时间，用于304

    /*public HTTPServer(){
        HTTPServer.modifiedFileTable = new FileTable();
        HTTPServer.modifiedFileTable.initInAFolder(BIND_DIR);
    }*/
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
            Request request = new Request(client);
            Response response = new Response(client, request);
            response.pushToClient(200,"");
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