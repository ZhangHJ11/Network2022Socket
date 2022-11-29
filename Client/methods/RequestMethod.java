package Client.methods;

import com.sun.xml.internal.ws.wsdl.writer.document.soap.Body;

import java.io.IOException;
import java.io.InputStream;

public interface RequestMethod {
    public abstract void ConductResponse(InputStream input,String s) throws IOException;
    public abstract void SendRequest(String s, boolean isKeepAlive, Body body) throws IOException;
}
