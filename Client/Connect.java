package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connect {
    private Socket ClientSocket;
    private InputStream ReceiveStream;
    private OutputStream SendStream;
    private int port;
    private String host;
    private boolean isKeepAlive;
    public Connect(String host,int port,boolean isKeepAlive){
        this.host=host;
        this.port=port;
        this.isKeepAlive=isKeepAlive;
    }

    public boolean isPersistent(){return isKeepAlive;}//TCP监测该链接是否有效
    public String getHost(){return host;}
    public int getPort(){return port;}
    public InputStream getReceiveStream(){return ReceiveStream;}
    public OutputStream getSendStream(){return SendStream;}
    public boolean isClosed(){return ClientSocket.isClosed();}
    public void close() throws IOException{
        ClientSocket.close();
    }

    public void creat(){
        try {
            ClientSocket=new Socket(host,port);
            ClientSocket.setKeepAlive(isKeepAlive);
            ReceiveStream=ClientSocket.getInputStream();
            SendStream=ClientSocket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
