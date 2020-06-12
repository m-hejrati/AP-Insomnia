package controller;

import model.MakeRequest;
import model.Request;
import model.Response;
import view.RightPanel;

import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class controlMakeRequest extends SwingWorker<Response, Request> {

    private Request requestInformation;
    private Response responseInformation;
    private RightPanel rightPanel;

    public controlMakeRequest(Request requestInformation, RightPanel rightPanel){

        this.requestInformation = requestInformation;
        this.rightPanel = rightPanel;

    }

    protected Response doInBackground() {
        MakeRequest makeRequest = new MakeRequest();
        try {
            responseInformation = makeRequest.makeReq(requestInformation);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseInformation;
    }

    protected void done() {

        rightPanel.setStatusCodeButton(responseInformation.getResponseCode());
        rightPanel.setStatusMessageButton(responseInformation.getResponseMessage());
        rightPanel.setTimeButton(responseInformation.getTime() / 1000000 + "ms");

        rightPanel.removeHeader();
        Map<String, List<String>> headers = responseInformation.getHeaders();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            Object[] data = {entry.getKey(), entry.getValue()};
            rightPanel.addHeaderTable(data);
        }

    }
}
