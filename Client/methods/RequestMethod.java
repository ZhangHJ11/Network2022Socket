package Client.methods;

import java.io.IOException;
import java.io.InputStream;

public interface RequestMethod {
    public abstract void conductResponse(InputStream input) throws IOException;

    public abstract void sendRequest(String s, boolean isKeepAlive) throws IOException;
}
