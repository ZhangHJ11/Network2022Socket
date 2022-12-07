package server;

public class returnValue {
    public int statusCode;
    public String location;

    public returnValue() {
        statusCode = 0;
        location = null;
    }

    public returnValue(int statusCode, String location) {
        this.statusCode = statusCode;
        this.location = location;
    }
}
