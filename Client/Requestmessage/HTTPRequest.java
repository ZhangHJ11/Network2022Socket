package Client.Requestmessage;

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
        if(requestBody!=null) request.append(requestBody.getBody());
        return request.toString();
    }
}
