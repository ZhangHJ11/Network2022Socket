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

    /**
     * 客户端发送报文的toString方法，对于body不是字符串内容的报文，这个方法会导致乱码
     * @return
     */
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

    /**
     * body转化成bytes方法
     * 用于组装报文，同时不会损坏格式
     * @return
     * @throws IOException
     */
    public byte[] bodytobyte() throws IOException {
        return requestBody.getBytes();
    }
}
