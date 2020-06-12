package model;

import java.util.List;
import java.util.Map;

public class Response {

    private int responseCode;
    private String responseMessage;
    private String body;
    private Map<String, List<String>> headers;
    private long time;

    public Response() {

    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public String getBody() {
        return body;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public long getTime() {
        return time;
    }

    public void print(boolean headerFlag) {

        System.out.println("Response Code: \t\t " + responseCode);
        System.out.println("Response Message: \t " + responseMessage);
        System.out.println("Body: \t\t " + body);
        if (headerFlag)
            System.out.println("Headers: \t " + headers);
        System.out.println();
    }
}
