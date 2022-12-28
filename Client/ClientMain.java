package Client;

import util.StreamReader;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        int port = 5689;
        String host = "127.0.0.1";
        System.out.println("Do you want to keep alive?(Y/N)");
        String cmd = StreamReader.readline();
        if (cmd.equals("Y")) {
//            启动长连接
            NewClient client = new NewClient(port, host, true);
        } else if (cmd.equals("N")) {
//            启动短链接
            NewClient client = new NewClient(port, host, false);
        }
    }
}
