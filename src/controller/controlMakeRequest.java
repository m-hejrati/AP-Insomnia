package controller;

import model.MakeRequest;
import model.Request;
import model.Response;
import view.RightPanel;

import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * this class handel making request with swing worker not to freeze the app
 *
 * @author Mahdi Hejarti 9723100
 * @since 2020.06.10
 */

public class controlMakeRequest extends SwingWorker<Response, Request> {

    private Request requestInformation;
    private Response responseInformation;
    private RightPanel rightPanel;

    /**
     * constructor for class
     * @param requestInformation information of request
     * @param rightPanel right panel to show response there
     */
    public controlMakeRequest(Request requestInformation, RightPanel rightPanel){

        this.requestInformation = requestInformation;
        this.rightPanel = rightPanel;
    }

    /**
     * make request in this method not to freeze the app
     * @return response of our request
     */
    protected Response doInBackground() {
        MakeRequest makeRequest = new MakeRequest();
        try {
            responseInformation = makeRequest.makeReq(requestInformation);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseInformation;
    }

    /**
     * show response in right panel
     */
    protected void done() {

        try {

            rightPanel.setStatusCodeButton(responseInformation.getResponseCode());
            if (responseInformation.getResponseMessage() != null)
                rightPanel.setStatusMessageButton(responseInformation.getResponseMessage());
            else
                rightPanel.setStatusMessageButton("     ");
            rightPanel.setTimeButton(responseInformation.getTime() / 1000000 + "ms");

            rightPanel.removeHeader();
            Map<String, List<String>> headers = responseInformation.getHeaders();
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                Object[] data = {entry.getKey(), entry.getValue()};
                rightPanel.addHeaderTable(data);
            }

        }catch (Exception e){
            System.err.println("Error in getting response");;
        }
    }

}