package model;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MakeRequest {

    public MakeRequest() {

    }

    public Response makeReq(Request myRequest) throws IOException {

        long startTime = System.nanoTime();

        HttpURLConnection connection = null;
        URL url = null;
        try {
            url = new URL(myRequest.getUrl());
        } catch (Exception e) {
            System.err.println("Invalid url");
        }

        try {

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(myRequest.getMethod());

        } catch (IOException e) {
//            e.printStackTrace();
            System.err.println("Impossible to make connection");
        }


        if (myRequest.getHeaders() != null) {

            String[] headers = myRequest.getHeaders().split(";");

            for (String header : headers) {
                String[] head = header.split(":");
                connection.setRequestProperty(head[0], head[1]);
            }
        }

        Response myResponse = new Response();


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


        System.out.println("Send Http " + connection.getRequestMethod() + " request \n");

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        myResponse.setTime(duration);

        return myResponse;
    }

    public void getResponse(HttpURLConnection connection, Response myResponse, Request myRequest) {

        try {
            myResponse.setResponseCode(connection.getResponseCode());
            myResponse.setResponseMessage(connection.getResponseMessage());

            Map<String, List<String>> map = connection.getHeaderFields();
            myResponse.setHeaders(map);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            InputStream is = connection.getInputStream();

            StringBuilder response = new StringBuilder();

            OutputStream os = null;
            String responseFileAddress = myRequest.getResponseFileAddress();

            System.out.println(responseFileAddress);
            if (connection.getContentType().equals("image/png")) {
                if (responseFileAddress.charAt(0) != '-')
                    os = new FileOutputStream(responseFileAddress);

                else {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDateTime now = LocalDateTime.now(); // get date of system
                    String date = dtf.format(now);

                    String address = "output_" + date;
                    os = new FileOutputStream(address);
                    myRequest.setResponseFileAddress(address);
                }
            }

            byte[] buffer = new byte[58];
            int byteReaded = is.read(buffer);
            while (byteReaded != -1) {

                if (os != null)
                    os.write(buffer, 0, byteReaded);

                for (byte b : buffer)
                    response.append((char) b);
                response.append("\n");

                byteReaded = is.read(buffer);
            }
            myResponse.setBody(response.toString());

            is.close();

            if (os != null)
                os.close();

        } catch (IOException e) {
//            e.printStackTrace();
            System.err.println("Impossible to save response.");
        }

    }

    public void sendFormData(HttpURLConnection connection, String bodies) {

        String[] body = bodies.split("&");

        HashMap<String, String> fooBody = new HashMap<>();
        for (String bod : body) {
            String[] bo = bod.split("=");
            fooBody.put(bo[0], bo[1]);
        }

        try {

//            URL url = new URL("http://apapi.haditabatabaei.ir/tests/post/formdata");
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            String boundary = System.currentTimeMillis() + "";
//            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            BufferedOutputStream request = new BufferedOutputStream(connection.getOutputStream());
            bufferOutFormData(fooBody, boundary, request);
//            BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
//            System.out.println(new String(bufferedInputStream.readAllBytes()));
//            System.out.println(connection.getResponseCode());
//            System.out.println(connection.getHeaderFields());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Impossible to send by form-data");
        }

    }

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

    public static void sendPOSTUploadBinary(HttpURLConnection connection, String fileAddress) {
        try {
//            URL url = new URL("http://apapi.haditabatabaei.ir/tests/post/binary");
            File newFile = new File(fileAddress);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/octet-stream");
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(connection.getOutputStream());
            BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(newFile));
            bufferedOutputStream.write(fileInputStream.readAllBytes());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
//            BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
//            System.out.println(new String(bufferedInputStream.readAllBytes()));
//            System.out.println(connection.getResponseCode());
//            System.out.println(connection.getHeaderFields());
        } catch (IOException e) {
//            e.printStackTrace();
            System.err.println("Missed file");
        }
    }

}
