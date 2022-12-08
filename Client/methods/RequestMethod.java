package Client.methods;

import Client.Requestmessage.RequestBody;

import java.io.IOException;
import java.io.InputStream;

public interface RequestMethod {
    public abstract void conductResponse(InputStream input) throws IOException;

    public abstract void sendRequest(String url, boolean isKeepAlive, RequestBody body) throws IOException;
}
