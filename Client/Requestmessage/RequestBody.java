package Client.Requestmessage;

//请求体
public class RequestBody {
    //Get method do not use this
    byte[] body = new byte[0];
    public RequestBody(byte[] body) { this.body = body; }
    public RequestBody() {

    }
}
