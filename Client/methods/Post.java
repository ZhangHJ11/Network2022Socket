package Client.methods;

import Client.Connect;
import Client.Connections;

import java.io.IOException;
import java.io.InputStream;

public class Post implements RequestMethod {
    String host;
    int port;
    Connections pool;

    public Post(String host, int port, Connections pool) {
        this.host = host;
        this.port = port;
        this.pool = pool;
    }

    public void conductResponse(InputStream inputStream) throws IOException {
        //实现 处理响应 的操作  对状态码做出处理
    }

    public void sendRequest(String s, boolean isKeepAlive) throws IOException {
        //实现发送请求
    }
}
