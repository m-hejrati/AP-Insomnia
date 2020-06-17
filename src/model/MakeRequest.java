package model;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this class make an http URL connection and send a request.
 *
 * @author Mahdi Hejarti 9723100
 * @since 2020.05.29
 */

public class MakeRequest {

    /**
     * this is the major method that make request.
     *
     * @param myRequest information of request
     * @return response
     */
    public Response makeReq(Request myRequest){

        long startTime = System.nanoTime();
        boolean flag = true;

        HttpURLConnection connection = null;
        URL url = null;

        try {
            url = new URL(myRequest.getUrl());
        } catch (Exception e) {
            System.err.println("Invalid url");
            flag = false;
        }

        if (flag)
            try {
                if ("http".equals(url.getProtocol()))
                    connection = (HttpURLConnection) url.openConnection();
                else if ("https".equals(url.getProtocol()))
                    connection = (HttpsURLConnection) url.openConnection();
                else {
                    System.err.println("UNSUPPORTED PROTOCOL!");
                    flag = false;
                }
            }catch (Exception e){
                System.err.println("unable to open connection");
                flag = false;
            }

        if(flag)
            try {
                connection.setRequestMethod(myRequest.getMethod());
            } catch (IOException e) {
                System.err.println("Impossible to make connection");
            }

        if (myRequest.getHeaders() != null) {

            String[] headers = myRequest.getHeaders().split(";");

            for (String header : headers) {
                String[] head = header.split(":");
                if (head.length == 2)
                    connection.setRequestProperty(head[0], head[1]);
            }
        }

        Response myResponse = new Response();

        if (flag)
            switch (myRequest.getMethod()) {
                case "GET":
                case "DELETE":
                    getResponse(connection, myResponse, myRequest);
                    break;

                case "POST":
                    if (myRequest.getBodyMethod() != null) {

                        if (myRequest.getBodyMethod().equals("--data")) {
                            sendFormData(connection, myRequest.getBody());
                            getResponse(connection, myResponse, myRequest);

                        } else if (myRequest.getBodyMethod().equals("--upload")) {
                            sendPOSTUploadBinary(connection, myRequest.getFileLoadAddress());
                            getResponse(connection, myResponse, myRequest);
                        }
                    } else {
                        System.err.println("Please Enter body method");
                    }
                    break;

                case "PUT":
                    if (myRequest.getBodyMethod() != null) {

                        sendFormData(connection, myRequest.getBody());
                        getResponse(connection, myResponse, myRequest);

                    } else {
                        System.err.println("Please Enter body method");
                    }
                    break;
            }

        if (flag)
            System.out.println("Http " + connection.getRequestMethod() + " request sent\n");

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        myResponse.setTime(duration);

        return myResponse;
    }

    /**
     * get the response of sent request
     *
     * @param connection Http URL connection
     * @param myResponse response information
     * @param myRequest request information
     */
    public void getResponse(HttpURLConnection connection, Response myResponse, Request myRequest) {

        try {

            myResponse.setResponseCode(connection.getResponseCode());
            if(connection.getResponseMessage() != null)
                myResponse.setResponseMessage(connection.getResponseMessage());
            else
                myResponse.setResponseMessage("    ");

            Map<String, List<String>> map = connection.getHeaderFields();
            myResponse.setHeaders(map);

        } catch (IOException e) {
            System.err.println("Impossible to get response");
        }

        try {

            InputStream inputStream = connection.getInputStream();

            StringBuilder response = new StringBuilder();

            OutputStream outputStream = null;
            String responseFileAddress = myRequest.getResponseFileAddress();

            myResponse.setContentType(connection.getContentType());

            if (myResponse.getContentType() != null)
                if (myResponse.getContentType().equals("image/png")) {
                    if (responseFileAddress.charAt(0) != '-')
                        outputStream = new FileOutputStream(responseFileAddress);

                    else {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDateTime now = LocalDateTime.now(); // get date of system
                        String date = dtf.format(now);

                        String address = "output_" + date;
                        outputStream = new FileOutputStream(address);
                        myRequest.setResponseFileAddress(address);
                    }
                }

                byte[] buffer = new byte[58];
                int byteReaded = inputStream.read(buffer);
                while (byteReaded != -1) {

                    if (outputStream != null)
                        outputStream.write(buffer, 0, byteReaded);

                    for (byte b : buffer)
                        response.append((char) b);
                    response.append("\n");

                    byteReaded = inputStream.read(buffer);
                }
                myResponse.setBody(response.toString());

                inputStream.close();

                if (outputStream != null)
                    outputStream.close();

        } catch (IOException e) {
            System.err.println("Impossible to save response.");
        }

    }

    /**
     * this class send request with form-data body
     *
     * @param connection Http URL connection
     * @param bodies     bodies to send
     */
    public void sendFormData(HttpURLConnection connection, String bodies) {

        String[] body = bodies.split("&");

        HashMap<String, String> fooBody = new HashMap<>();
        for (String bod : body) {
            String[] bo = bod.split("=");
            fooBody.put(bo[0], bo[1]);
        }

        try {

            String boundary = System.currentTimeMillis() + "";
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            BufferedOutputStream request = new BufferedOutputStream(connection.getOutputStream());
            bufferOutFormData(fooBody, boundary, request);

        } catch (Exception e) {
            System.err.println("Impossible to send by form-data");
        }

    }

    /**
     * main part of this method copied from Mr hadi tabatabaei
     * set setting of form-data body
     *
     * @param body                 body of request to send
     * @param boundary             boundary to make form-data
     * @param bufferedOutputStream buffer to hold data
     * @throws IOException exceptions in working with stream
     */
    public static void bufferOutFormData(HashMap<String, String> body, String boundary, BufferedOutputStream bufferedOutputStream) throws IOException {

        for (String key : body.keySet()) {

            bufferedOutputStream.write(("--" + boundary + "\r\n").getBytes());

            if (key.contains("file")) {
                bufferedOutputStream.write(("Content-Disposition: form-data; filename=\"" + (new File(body.get(key))).getName() + "\"\r\nContent-Type: Auto\r\n\r\n").getBytes());

                try {
                    BufferedInputStream tempBufferedInputStream = new BufferedInputStream(new FileInputStream(new File(body.get(key))));
                    byte[] filesBytes = tempBufferedInputStream.readAllBytes();
                    bufferedOutputStream.write(filesBytes);
                    bufferedOutputStream.write("\r\n".getBytes());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {

                bufferedOutputStream.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n").getBytes());
                bufferedOutputStream.write((body.get(key) + "\r\n").getBytes());
            }
        }

        bufferedOutputStream.write(("--" + boundary + "--\r\n").getBytes());
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }

    /**
     * main part of this method copied from Mr hadi tabatabaei
     *
     * set setting uploaded body
     *
     * @param connection  Http URL connection
     * @param fileAddress address to load file
     */
    public static void sendPOSTUploadBinary(HttpURLConnection connection, String fileAddress) {

        try {

            File newFile = new File(fileAddress);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/octet-stream");
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(connection.getOutputStream());
            BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(newFile));
            bufferedOutputStream.write(fileInputStream.readAllBytes());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();

        } catch (IOException e) {
            System.err.println("Missed file");
        }
    }

}