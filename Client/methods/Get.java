package Client.methods;

import Client.Connections;

import java.io.IOException;
import java.io.InputStream;

public class Get implements RequestMethod {
    String host;
    int port;
    Connections pool;

    public Get(String host,int port,Connections pool){
        this.host=host;
        this.port=port;
        this.pool=pool;
    }
    public void ConductResponse(InputStream inputStream,String s) throws IOException{
        //实现 处理响应 的操作  对状态码做出处理
    }
    public void SendRequest(String s,boolean isKeepAlive) throws IOException{
        //实现发送请求
    }
}
