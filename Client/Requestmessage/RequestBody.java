package Client.Requestmessage;

import util.StreamReader;

import java.io.IOException;
import java.io.InputStream;

public class RequestBody {
    private byte[] bytes;

    public RequestBody(String str) {
        this.bytes = str.getBytes();
    }

    public RequestBody(InputStream inputStream) throws IOException {
        this.bytes = StreamReader.getBytes(inputStream);
    }

    public RequestBody() {
    }

    public byte[] getBytes() {
        return bytes;
    }

    public int getlength(){
        return bytes.length;
    }
}
