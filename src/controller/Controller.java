package controller;

import model.MakeRequest;
import model.Request;
import model.Response;
import model.Save;
import view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class Controller {

    private View view;
    private Request requestInformation;
    private Response responseInformation;
    private MakeRequest makeRequest;
    private Save save;


    public Controller(View view) {

        this.view = view;
        requestInformation = new Request();
        responseInformation = new Response();
        save = new Save();

    }

    public String[][] loadRequest() {
        return save.list();
    }

    public void setMethod(int selected, String[] URLOptions) {
        requestInformation.setMethod(URLOptions[selected]);
    }

    public void setURL(String url) {
        requestInformation.setUrl(url);
    }

    public void setBodyMethod(String bodyMethod) {
        requestInformation.setBodyMethod(bodyMethod);
    }

    public void makeReq(){
        controlMakeRequest task = new controlMakeRequest(requestInformation, view.getRightPanel());
        task.execute();
        try {
            responseInformation = task.get();
        } catch (Exception e) {
            System.err.println("Error in getting response from controlMakeRequest");
        }
    }

    public void copyToClipboard() {

        String data = "";
        Map<String, java.util.List<String>> headers = responseInformation.getHeaders();
        if (headers != null)
            for (Map.Entry<String, List<String>> entry : headers.entrySet())
                data += entry.getKey() + ":" + entry.getValue() + "\n";

        StringSelection stringSelection = new StringSelection(data);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

    }

    public void rawResponse(JTextArea raw) {

        raw.setText(responseInformation.getBody());

    }
}
