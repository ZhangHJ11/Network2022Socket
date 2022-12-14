package Client.methods;

import Client.Requestmessage.RequestBody;

import java.io.IOException;

public interface RequestMethod {
    void conductResponse(String url) throws IOException;

    void sendRequest(String url, RequestBody body) throws IOException;
}
