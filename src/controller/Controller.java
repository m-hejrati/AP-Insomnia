package controller;

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

/**
 * controller class set connection between model and view in mvc design pattern
 *
 * @author Mahdi Hejarti 9723100
 * @since 2020.06.10
 */

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

    /**
     * load list of saved requests from file
     * @return requests list
     */
    public String[][] loadRequestList() {
        return save.list();
    }

    /**
     * load information of an specific request from file
     * @param address name and group
     */
    public void loadRequest(String address) {

        String[] ary = address.split(",");
        String group = ary[1].trim();
        String name = ary[2].substring(1, ary[2].length() - 1);

        String path = "requests/" + group;
        requestInformation = save.load(path + "/" + name);

        view.getCenterPanel().setURL(requestInformation.getUrl());
        view.getCenterPanel().setList(requestInformation.getMethod());

        view.getCenterPanel().removePrevious();

        String[] headers = requestInformation.getHeaders().split(";");
        for (String header : headers) {
            String[] head = header.split(":");
            view.getCenterPanel().setHeader(head);
        }

        String[] bodies = requestInformation.getBody().split("&");
        for (String body : bodies) {
            String[] bod = body.split("=");
            view.getCenterPanel().setBody(bod);
        }

    }

    /**
     * save request in file
     * @param name name of request
     * @param group group of request
     */
    public void saveReq(String name, String group) {

        String path = "requests/" + group;
        if (! new File(path).exists())
            save.createGroup(group);

        save.save(name, group, requestInformation);

        view.getLeftPanel().removeAll();
        view.getLeftPanel().loadTree(this);
        view.getLeftPanel().updateUI();
        view.getLeftPanel().repaint();
        view.getLeftPanel().revalidate();
    }

    /**
     * make the request with swing work
     */
    public void makeReq() {

        controlMakeRequest task = new controlMakeRequest(requestInformation, view.getRightPanel());
        task.execute();
        try {
            responseInformation = task.get();
        } catch (Exception e) {
            e.printStackTrace();
            //            System.err.println("Error in getting response from controlMakeRequest");
        }
    }

    /**
     * set method of request
     * @param selected selected method
     * @param URLOptions all the possible option to choose
     */
    public void setMethod(int selected, String[] URLOptions) {
        requestInformation.setMethod(URLOptions[selected]);
    }

    /**
     * set url of request
     * @param url url
     */
    public void setURL(String url) {
        requestInformation.setUrl(url);
    }

    /**
     * set body method
     * @param bodyMethod body method
     */
    public void setBodyMethod(String bodyMethod) {
        requestInformation.setBodyMethod(bodyMethod);
    }

    /**
     * copy the header to clipboard
     */
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

    /**
     * show body response
     * @param raw body response
     */
    public void rawResponse(JTextArea raw) {
        raw.setText(responseInformation.getBody());
    }

    /**
     * show picture of response
     * @param bodyPanel panel to show response body
     */
    public void previewResponse(JPanel bodyPanel) {

        if (responseInformation.getContentType().toLowerCase().contains("image/png")) {

            File file = new File(requestInformation.getResponseFileAddress());
            BufferedImage myPicture = null;
            try {
                myPicture = ImageIO.read(file);
            } catch (IOException e) {
                System.err.println("Error in loading image from system");
            }

            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            bodyPanel.add(picLabel);

        } else if (responseInformation.getContentType().toLowerCase().contains("text/html")) {

            JEditorPane jep = new JEditorPane();
            jep.setEditable(false);
            try {
                jep.setPage(requestInformation.getUrl());
            }catch (IOException e) {
                jep.setContentType("text/html");
                jep.setText("<html>Could not load</html>");
            }

            JScrollPane scrollPane = new JScrollPane(jep);
            bodyPanel.add(scrollPane);

        } else
            bodyPanel.add(new JLabel("Preview"));
    }

    /**
     * set address of file to load
     * @param selectedFile address of file
     */
    public void fileLoadAddress(String selectedFile) {
        requestInformation.setFileLoadAddress(selectedFile);
    }

    /**
     * set queries to send
     * @param queriesPanelList list of queries
     * @param queryField query field
     */
    public void setQueries(ArrayList<JPanel> queriesPanelList, JTextField queryField) {
        String readyHeaders = analyze(queriesPanelList, "&", "=");
        String newURL = requestInformation.getUrl() + "?" + readyHeaders;
        requestInformation.setUrl(newURL);
        queryField.setText(newURL);
    }

    /**
     * set request header to send
     * @param headersPanelList list of headers
     */
    public void setHeaders(ArrayList<JPanel> headersPanelList) {
        String readyHeaders = analyze(headersPanelList, ";", ":");
        requestInformation.setHeaders(readyHeaders);
    }

    /**
     * set request body in form-data method
     * @param bodiesPanelList list of bodies
     */
    public void setBody(ArrayList<JPanel> bodiesPanelList){
        String readyHeaders = analyze(bodiesPanelList, "&", "=");
        requestInformation.setBody(readyHeaders);
    }

    /**
     * get panel and set the entered key value of headers
     * @param panelList list of panels
     * @param major regex to split two of them
     * @param minor regex to split key and value
     * @return ready string to send
     */
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