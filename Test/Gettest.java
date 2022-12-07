package Test;

import Client.NewClient;

import java.io.IOException;

public class Gettest {
    boolean keepalive = false;

    // get keepalive
    public void NonPersistentGet(int port, String host) throws IOException {
        keepalive = false;
        NewClient client = new NewClient(port, host, "Get");
        client.Get("." + "/" + "Resources" + "/" + "3.zip", keepalive);
    }

    // get do not keepalive
    public void PersistentGet(int port, String host) throws IOException {
        NewClient client = new NewClient(port, host, "Get");
        client.Get("." + "/" + "Resources" + "/" + "index.html", keepalive);
    }

}
