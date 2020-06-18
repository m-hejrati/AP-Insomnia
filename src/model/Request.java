package model;

import java.io.Serializable;

/**
 * this class holds information of our request.
 *
 * @author Mahdi Hejarti 9723100
 * @since 2020.05.29
 */

public class Request implements Serializable {

    private String url;
    private String method; // between GET, POST, DELETE, PUT, PATCH.
    private String headers;
    private String bodyMethod; // between form-data and upload
    private String body;
    private String fileLoadAddress;
    private String responseFileAddress;
    private boolean showHeaderResponse;

    /**
     * constructor of request class
     */
    public Request(){

        url = "https://www.google.com"; // default url
        method = "GET"; // default method
        headers = null;
        body = null;
        bodyMethod = "--data"; // default method
        fileLoadAddress = null;
        responseFileAddress = "-";
    }

    /**
     * setter for url
     * @param url entered url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * setter for method
     * @param method entered method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * setter for body
     * @param body entered body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * setter for header
     * @param headers entered header
     */
    public void setHeaders(String headers) {
        this.headers = headers;
    }

    /**
     * setter for body method
     * @param bodyMethod entered method
     */
    public void setBodyMethod(String bodyMethod) {
        this.bodyMethod = bodyMethod;
    }

    /**
     * setter for address to load file
     * @param fileLoadAddress entered address to load file from
     */
    public void setFileLoadAddress(String fileLoadAddress) {
        this.fileLoadAddress = fileLoadAddress;
    }

    /**
     * setter for response file address
     * @param responseFileAddress entered address to save response in
     */
    public void setResponseFileAddress(String responseFileAddress) {
        this.responseFileAddress = responseFileAddress;
    }

    /**
     * setter for show header response
     * @param showHeaderResponse boolean to know if show headers or not in response
     */
    public void setShowHeaderResponse(boolean showHeaderResponse) {
        this.showHeaderResponse = showHeaderResponse;
    }

    /**
     * getter for url
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * getter for method
     * @return method
     */
    public String getMethod() {
        return method;
    }

    /**
     * getter for body
     * @return body
     */
    public String getBody() {
        return body;
    }

    /**
     * getter for headers
     * @return headers
     */
    public String getHeaders() {
        return headers;
    }

    /**
     * getter for body method
     * @return body method
     */
    public String getBodyMethod() {
        return bodyMethod;
    }

    /**
     * getter for File load address
     * @return entered address to load file from
     */
    public String getFileLoadAddress() {
        return fileLoadAddress;
    }

    /**
     * getter for response file address
     * @return entered address to save response in
     */
    public String getResponseFileAddress() {
        return responseFileAddress;
    }

    /**
     * getter for show header response
     * @return boolean to know if show headers or not in response
     */
    public boolean isShowHeaderResponse() {
        return showHeaderResponse;
    }

    /**
     * print saved request information
     */
    public void print(){
        System.out.print(" | url: " + url);
        System.out.print(" | method: " + method);
        System.out.print(" | body method: " + bodyMethod);
        System.out.print(" | body: " + body);
        System.out.println();
    }
}