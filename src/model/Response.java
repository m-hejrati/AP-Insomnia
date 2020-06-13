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

}