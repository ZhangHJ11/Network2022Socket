package Client.Requestmessage;

import java.io.InputStream;

public class RequestBody {

    private String body;
    private InputStream inputStreambody;

    public RequestBody(String body) {
        this.body = body;
    }

    public RequestBody(InputStream inputStream) {
        this.inputStreambody = inputStream;
    }

    public RequestBody() {
    }

    public String getBody() {
        return this.body;
    }

    public InputStream getInputStreambody(){return this.inputStreambody;}
}
