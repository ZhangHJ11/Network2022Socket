package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

public class Connect {
    private Socket server;
    private InputStream receiveStream;
    private OutputStream sendStream;
    private final int port;
    private final String host;
    private boolean isKeepAlive;

    public Connect(String host, int port, boolean isKeepAlive) {
        this.host = host;
        this.port = port;
        this.isKeepAlive = isKeepAlive;
    }

    public boolean isPersistent() {
        return isKeepAlive;
    }//TCP监测该链接是否有效

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public InputStream getReceiveStream() {
        return receiveStream;
    }

    public Socket getServer() {
        return this.server;
    }
    public OutputStream getSendStream() {
        return sendStream;
    }

    public void close() throws IOException {
        server.close();
    }

    public void creat() {
        //建立连接
        try {
            server = new Socket(host, port);
            server.setKeepAlive(isKeepAlive);
            receiveStream = server.getInputStream();
            sendStream = server.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setKeepAlive(boolean isKeepAlive) throws SocketException {
        server.setKeepAlive(isKeepAlive);
        this.isKeepAlive=isKeepAlive;
    }
}
