package controller;

import model.MakeRequest;
import model.Request;
import model.Response;
import view.RightPanel;

import javax.swing.*;
import java.util.List;
import java.util.Map;


/**
 * this class handel making request with swing worker not to freeze the app
 *
 * @author Mahdi Hejarti 9723100
 * @since 2020.06.10
 */

public class ControlMakeRequest extends SwingWorker<Response, Request> {

    private Request requestInformation;
    private Response responseInformation;
    private RightPanel rightPanel;
    private Controller controller;

    /**
     * constructor for class
     * @param requestInformation information of request
     * @param rightPanel right panel to show response there
     * @param controller controller to set response
     */
    public ControlMakeRequest(Request requestInformation, RightPanel rightPanel, Controller controller){

        this.requestInformation = requestInformation;
        this.rightPanel = rightPanel;
        this.controller = controller;

    }

    /**
     * make request in this method not to freeze the app
     * @return response of our request
     */
    protected Response doInBackground() {
        MakeRequest makeRequest = new MakeRequest();

        responseInformation = makeRequest.makeReq(requestInformation);
        controller.setResponse(responseInformation);

        return responseInformation;
    }

    /**
     * show response in right panel
     */
    protected void done() {

        try {

            rightPanel.setStatusCodeButton(responseInformation.getResponseCode());
            rightPanel.setStatusMessageButton(responseInformation.getResponseMessage());
            rightPanel.setTimeButton(responseInformation.getTime() / 1000000 + "ms");

            rightPanel.removeHeader();

            if (responseInformation.getHeaders() != null) {
                Map<String, List<String>> headers = responseInformation.getHeaders();
                for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                    Object[] data = {entry.getKey(), entry.getValue()};
                    rightPanel.addHeaderTable(data);
                }
            }

        }catch (Exception e){
            System.err.println("Error in getting response");;
            e.printStackTrace();
        }
    }
}