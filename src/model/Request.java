package model;

import java.io.Serializable;

public class Request implements Serializable {

    private String url; //ok
    private String method; //ok
    private String headers; //TODO: difficult
    private String bodyMethod; //ok
    private String body; // TODO: oh oh
    private String fileLoadAddress;
    private String responseFileAddress;


    public Request(){

        url = "https://www.google.com"; // default url
        method = "GET"; // default method
        headers = null;
        body = null;
        bodyMethod = null;
        fileLoadAddress = null;
        responseFileAddress = null;

    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public void setBodyMethod(String bodyMethod) {
        this.bodyMethod = bodyMethod;
    }

    public void setFileLoadAddress(String fileLoadAddress) {
        this.fileLoadAddress = fileLoadAddress;
    }

    public void setResponseFileAddress(String responseFileAddress) {
        this.responseFileAddress = responseFileAddress;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public String getBody() {
        return body;
    }

    public String getHeaders() {
        return headers;
    }

    public String getBodyMethod() {
        return bodyMethod;
    }

    public String getFileLoadAddress() {
        return fileLoadAddress;
    }

    public String getResponseFileAddress() {
        return responseFileAddress;
    }

    public void print(){
        System.out.print(" | url: " + url);
        System.out.print(" | method: " + method);
        System.out.print(" | body method: " + bodyMethod);
        System.out.print(" | body: " + body);
        System.out.println();

    }
}