package Client.Requestmessage;

import java.io.IOException;

public class HTTPRequest {
    public RequestLine requestLine;
    public RequestHead requestHead;
    public RequestBody requestBody;

    public HTTPRequest(RequestLine requestLine, RequestHead requestHead, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHead = requestHead;
        this.requestBody = requestBody;
    }

    @Override
    public String toString() {
        StringBuilder request=new StringBuilder();
        request.append(requestLine.getMethod()).append(" ").append(requestLine.getUrl()).append(" ").append(requestLine.getVersion()).append(System.lineSeparator());
        for(String object:requestHead.getHashMap().keySet()) {
            request.append(object).append(": ").append(requestHead.getHashMap().get(object)).append(System.lineSeparator());
        }
        request.append("\r\n");
        return request.toString();
    }

    public byte[] bodytobyte() throws IOException {
        return requestBody.getBytes();
    }
}
