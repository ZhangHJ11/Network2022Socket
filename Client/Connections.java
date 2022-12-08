package Client;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 提供多个链接
 */
public class Connections {
    //实现链接池
    private HashMap<String, Connect> pool = new LinkedHashMap<>();

    public Connect getConnection(String host, int port, boolean isKeepAlive) throws IOException {
        Connect con = pool.get(host);
        if (con != null) {
            if (!con.isPersistent()) {
                pool.remove(host);
            } else {
                return con;
            }
        }
        con = new Connect(host, port, isKeepAlive);
        pool.put(host, con);
        con.creat();
        return con;
    }

    public void removeConnection(String host) {
        Connect con = pool.get(host);
        if (con != null) {
            try {
                con.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        pool.remove(host);
    }
}
