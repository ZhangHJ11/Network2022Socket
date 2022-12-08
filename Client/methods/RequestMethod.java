package Client.methods;

import Client.Connect;
import Client.Requestmessage.RequestBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface RequestMethod {
    void conductResponse(InputStream input) throws IOException;

    void sendRequest(String url, RequestBody body) throws IOException;
}
