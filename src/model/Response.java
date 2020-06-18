package model;

import java.util.List;
import java.util.Map;

/**
 * this class holds information of our response.
 *
 * @author Mahdi Hejarti 9723100
 * @since 2020.05.29
 */

public class Response {

    private int responseCode;
    private String responseMessage;
    private String body;
    private Map<String, List<String>> headers;
    private long time;
    private String contentType;


    /**
     * setter for response code
     * @param responseCode response code
     */
    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * setter for response message
     * @param responseMessage response message
     */
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    /**
     * setter for body
     * @param body body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * setter for headers
     * @param headers headers
     */
    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    /**
     * setter for time
     * @param time time
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * setter for content-type
     * @param contentType content-type
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * getter for response code
     * @return response code
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * getter for response message
     * @return response message
     */
    public String getResponseMessage() {
        return responseMessage;
    }

    /**
     * getter for body
     * @return body response
     */
    public String getBody() {
        return body;
    }

    /**
     * getter for header response
     * @return header response
     */
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    /**
     * getter for time
     * @return time
     */
    public long getTime() {
        return time;
    }

    /**
     * getter for content-type
     * @return content-type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * print response information
     * @param headerFlag flag to know if show headers or not in response.
     */
    public void print(boolean headerFlag) {

        System.out.println("Response Code: \t\t " + responseCode);
        System.out.println("Response Message: \t " + responseMessage);
        System.out.println("Body: \t\t " + body);
        if (headerFlag)
            System.out.println("Headers: \t " + headers);
        System.out.println();

    }
}