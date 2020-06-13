package controller;

import model.MakeRequest;
import model.Request;
import model.Response;
import model.Save;
import view.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class Controller {

    private View view;
    private Request requestInformation;
    private Response responseInformation;
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

    public void saveReq() {

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

    public void previewResponse(JPanel bodyPanel) {

        File file = new File (requestInformation.getResponseFileAddress());

        if (file.exists()) {

            BufferedImage myPicture = null;
            try {
                myPicture = ImageIO.read(file);
            } catch (IOException e) {
//                e.printStackTrace();
                System.err.println("Error in loading image from system");
            }
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            bodyPanel.add(picLabel);

        }else
            bodyPanel.add(new JLabel("Preview"));

    }

    public void fileLoadAddress(String selectedFile) {
        requestInformation.setFileLoadAddress(selectedFile);
    }

    public void setHeaders(ArrayList<JPanel> headersPanelList) {
        String readyHeaders = analyze(headersPanelList, ";", ":");
        requestInformation.setHeaders(readyHeaders);
    }

    public void setQueries(ArrayList<JPanel> queriesPanelList, JTextField queryField) {

        String readyHeaders = analyze(queriesPanelList, "&", "=");
        String newURL = requestInformation.getUrl() + "?" + readyHeaders;
        requestInformation.setUrl(newURL);
        queryField.setText(newURL);

    }

    public void setBody(ArrayList<JPanel> bodiesPanelList){
        String readyHeaders = analyze(bodiesPanelList, "&", "=");
        requestInformation.setBody(readyHeaders);

        System.out.println(readyHeaders);

    }

    public String analyze(ArrayList<JPanel> panelList, String major, String minor){

        String readyHeaders = "";

        for (JPanel panel : panelList) {

            BorderLayout layout = (BorderLayout) panel.getLayout();
            JPanel checkPanel = (JPanel) layout.getLayoutComponent(BorderLayout.EAST);
            JCheckBox checkBox = (JCheckBox) checkPanel.getComponent(0);
            if (checkBox.isSelected()) {
                JPanel textPanel = (JPanel) layout.getLayoutComponent(BorderLayout.CENTER);

                JTextField key = (JTextField) textPanel.getComponent(0);
                readyHeaders += key.getText();
                readyHeaders += minor;
                JTextField value = (JTextField) textPanel.getComponent(1);
                readyHeaders += value.getText();

                readyHeaders += major;
            }
        }

        return readyHeaders;

    }


}
