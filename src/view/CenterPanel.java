package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * in this class we design center part of the app
 *
 * @author Mahdi Hejarti 9723100
 * @since 2020.05.10
 */

public class CenterPanel extends JPanel {

    private JPanel northCenter; // north part of center panel
    private JTabbedPane centerCenter; // center part of center panel
    private JTextField URL;
    private JComboBox list;
    private JPanel boxHeaderPanel;
    private ArrayList<JPanel> headersPanelList;
    private JPanel boxBodyPanel;
    private ArrayList<JPanel> bodiesPanelList;

    /**
     * constructor for Center Panel
     *
     * @param controller controller object from controller class to set connection between model and view
     */
    public CenterPanel(Controller controller) {

        this.setLayout(new BorderLayout(5, 5));

        // design and add north part of the center panel
        northCenter = new JPanel(new BorderLayout(5, 5));
        northCenter.setBorder(new EmptyBorder(5, 5, 5, 5));
        String[] URLOptions = {"GET", "POST", "DELETE", "PUT", "PATCH"};

        list = new JComboBox(URLOptions);
        list.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JComboBox list = (JComboBox) event.getSource();
                int selected = list.getSelectedIndex();
                controller.setMethod(selected, URLOptions);
            }
        });
        northCenter.add(list, BorderLayout.WEST);

        URL = new JTextField("https://www.google.com");
        URL.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                // nothing
            }
            public void focusLost(FocusEvent event) {
                controller.setURL(URL.getText());
            }
        });
        northCenter.add(URL, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton send = new JButton("Send");
        send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                controller.makeReq();
            }
        });
        buttons.add(send);

        JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String[] res = saveOptionPane();
                controller.saveReq(res[0], res[1]);
            }
        });
        buttons.add(save);

        northCenter.add(buttons, BorderLayout.EAST);
        this.add(northCenter, BorderLayout.NORTH);

        centerCenter = new JTabbedPane();

        // design and add body panel
        bodiesPanelList = new ArrayList<JPanel>();

        JPanel bodyPanel = new JPanel(new BorderLayout(5, 5));
        JPanel buttonPanel3 = new JPanel(new FlowLayout());
        JButton addButton3 = new JButton("Add");

        boxBodyPanel = new JPanel();
        boxBodyPanel.setLayout(new BoxLayout(boxBodyPanel, BoxLayout.Y_AXIS));
        addButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                JPanel newPanel = addHeaderField(boxBodyPanel, bodiesPanelList);
                boxBodyPanel.add(newPanel);
                bodiesPanelList.add(newPanel);
                repaint();
                revalidate();
            }
        });
        buttonPanel3.add(addButton3);

        JButton setButton3 = new JButton("Set");
        setButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                controller.setBody(bodiesPanelList);
            }
        });
        buttonPanel3.add(setButton3);

        bodyPanel.add(buttonPanel3, BorderLayout.CENTER);
        bodyPanel.add(boxBodyPanel, BorderLayout.NORTH);

        centerCenter.add("Body", bodyPanel);
        JPanel tabComponentPanel = new JPanel();
        tabComponentPanel.add(new JLabel("Body"));
        centerCenter.setTabComponentAt(0, tabComponentPanel);

        tabComponentPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    centerCenter.setSelectedComponent(bodyPanel);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    showPopUpMenu(bodyPanel, e, controller);
                }
            }
        });

        // design and add header panel
        headersPanelList = new ArrayList<JPanel>();

        JPanel headerPanel = new JPanel(new BorderLayout(5, 5));
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add");

        boxHeaderPanel = new JPanel();
        boxHeaderPanel.setLayout(new BoxLayout(boxHeaderPanel, BoxLayout.Y_AXIS));
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                JPanel newPanel = addHeaderField(boxHeaderPanel, headersPanelList);
                boxHeaderPanel.add(newPanel);
                headersPanelList.add(newPanel);
                repaint();
                revalidate();
            }
        });
        buttonPanel.add(addButton);

        JButton setButton = new JButton("Set");
        setButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                controller.setHeaders(headersPanelList);
            }
        });
        buttonPanel.add(setButton);

        headerPanel.add(buttonPanel, BorderLayout.CENTER);
        headerPanel.add(boxHeaderPanel, BorderLayout.NORTH);
        centerCenter.add("Header", headerPanel);

        // design query panel
        ArrayList<JPanel> queriesPanelList = new ArrayList<JPanel>();

        JPanel queryPanel = new JPanel(new BorderLayout(5, 5));
        JPanel buttonPanel2 = new JPanel(new FlowLayout());
        JButton addButton2 = new JButton("Add");

        JPanel boxQueryPanel = new JPanel();
        boxQueryPanel.setLayout(new BoxLayout(boxQueryPanel, BoxLayout.Y_AXIS));
        boxQueryPanel.add(new JLabel("URL PREVIEW:", SwingConstants.LEFT));
        JTextField queryField = new JTextField("https://www.google.com");
        queryField.setBorder(new EmptyBorder(5, 5, 5, 5));
        boxQueryPanel.add(queryField);

        addButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                JPanel newPanel = addHeaderField(boxQueryPanel, queriesPanelList);
                boxQueryPanel.add(newPanel);
                queriesPanelList.add(newPanel);
                repaint();
                revalidate();
            }
        });
        buttonPanel2.add(addButton2);

        JButton setButton2 = new JButton("Set");
        setButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                controller.setQueries(queriesPanelList, queryField);
            }
        });
        buttonPanel2.add(setButton2);

        queryPanel.add(buttonPanel2, BorderLayout.CENTER);
        queryPanel.add(boxQueryPanel, BorderLayout.NORTH);
        centerCenter.add("Query", queryPanel);

        // design auth panel
        JPanel authPanel = new JPanel();
        authPanel.setLayout(new BoxLayout(authPanel, BoxLayout.Y_AXIS));

        JPanel tokenPanel = new JPanel(new FlowLayout());
        tokenPanel.setBorder(new EmptyBorder(5, 2, 2, 5));
        tokenPanel.add(new JLabel("TOKEN:         "));
        JTextField firstField = new JTextField();
        firstField.setPreferredSize(new Dimension(300, 20));
        tokenPanel.add(firstField);

        JPanel prefixPanel = new JPanel(new FlowLayout());
        prefixPanel.setBorder(new EmptyBorder(5, 2, 2, 5));
        prefixPanel.add(new JLabel("PREFIX:         "));
        JTextField secondField = new JTextField();
        secondField.setPreferredSize(new Dimension(300, 20));
        prefixPanel.add(secondField);

        JPanel enablePanel = new JPanel(new GridLayout(1, 2));
        enablePanel.setBorder(new EmptyBorder(10, 2, 2, 5));
        enablePanel.add(new JLabel("       ENABLE:"));
        enablePanel.add(new JCheckBox());

        authPanel.add(tokenPanel);
        authPanel.add(prefixPanel);
        authPanel.add(enablePanel);

        JPanel authPanel2 = new JPanel(new BorderLayout());
        authPanel2.add(authPanel, BorderLayout.NORTH);
        centerCenter.add("Auth", authPanel2);

        this.add(centerCenter, BorderLayout.CENTER);
    }

    /**
     * this method show pop up menu when we click on Body tab
     *
     * @param bodyPanel  body panel in tab
     * @param e          mouse event
     * @param controller
     */
    public void showPopUpMenu(JPanel bodyPanel, MouseEvent e, Controller controller) {
        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem dataItem = new JMenuItem("Form Data");
        dataItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                controller.setBodyMethod("--data");
            }
        });
//        JMenuItem jsonItem = new JMenuItem("JSON");
//        jsonItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ev) {
//                bodyPanel.removeAll();
//                controller.setBodyMethod("--JSON");
//                bodyPanel.add(new JLabel("JSON"));
//                repaint();
//                revalidate();
//            }
//        });
        JMenuItem binaryItem = new JMenuItem("Binary Data");
        binaryItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                chooser.showOpenDialog(null);
                controller.setBodyMethod("--upload");
                controller.fileLoadAddress(chooser.getSelectedFile().toString());
            }
        });

        jPopupMenu.add(dataItem);
//        jPopupMenu.add(jsonItem);
        jPopupMenu.add(binaryItem);

        jPopupMenu.setVisible(true);
        jPopupMenu.show(bodyPanel, e.getX(), e.getY());
    }

    /**
     * this method make key value field and check box and button in header tab
     *
     * @param boxPanel box panel that hold headers panel vertically
     * @param headersPanelList list of header panel
     * @return created panel
     */
    public JPanel addHeaderField(JPanel boxPanel, ArrayList<JPanel> headersPanelList) {

        JPanel textPanel = new JPanel(new GridLayout(1, 2));
        textPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
        textPanel.add(new JTextField("name"));
        textPanel.add(new JTextField("value"));

        JPanel checkPanel = new JPanel(new GridLayout(1, 2));
        checkPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
        checkPanel.add(new JCheckBox());

        JButton delButton = new JButton("\u2718");
        checkPanel.add(delButton);

        JPanel keyValuePanel = new JPanel(new BorderLayout(5, 5));
        keyValuePanel.add(textPanel, BorderLayout.CENTER);
        keyValuePanel.add(checkPanel, BorderLayout.EAST);

        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                boxPanel.remove(keyValuePanel);
                headersPanelList.remove(keyValuePanel);
                repaint();
                revalidate();
            }
        });

        return keyValuePanel;
    }

    /**
     * show an option pane to set name and group
     * @return entered name and group
     */
    public String[] saveOptionPane() {

        String name = "";
        String group = "";

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("name:"));
        JTextField nameField = new JTextField(5);
        myPanel.add(nameField);

        myPanel.add(Box.createHorizontalStrut(15));

        myPanel.add(new JLabel("group:"));
        JTextField groupField = new JTextField(5);
        myPanel.add(groupField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Enter name and group to save", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            name = nameField.getText();
            group = groupField.getText();
        }

        return new String[]{name, group};
    }

    /**
     * set loaded url in the app
     * @param url loaded url
     */
    public void setURL(String url) {
        URL.setText(url);
    }

    /**
     * set loaded method
     * @param option choose option
     */
    public void setList(String option) {
        String[] urlOptions = {"GET", "POST", "DELETE", "PUT", "PATCH"};
        list.setSelectedIndex(Arrays.asList(urlOptions).indexOf(option));
    }

    /**
     * this method just remove previous headers to show new ones
     */
    public void removePrevious() {

        boxHeaderPanel.removeAll();
        headersPanelList.removeAll(headersPanelList);

        boxBodyPanel.removeAll();
        bodiesPanelList.removeAll(bodiesPanelList);
    }

    /**
     * show loaded header
     * @param head header of request
     */
    public void setHeader(String[] head){

        JPanel newPanel = addHeaderField(boxHeaderPanel, headersPanelList);
        boxHeaderPanel.add(newPanel);
        headersPanelList.add(newPanel);

        setData(head, newPanel);
    }

    /**
     * show loaded body
     * @param bod body of request
     */
    public void setBody(String[] bod) {

        JPanel newPanel = addHeaderField(boxBodyPanel, bodiesPanelList);
        boxBodyPanel.add(newPanel);
        bodiesPanelList.add(newPanel);

        setData(bod, newPanel);
    }

    /**
     * set set common data of header and body
     * @param data data
     * @param newPanel new panel
     */
    public void setData(String[] data, JPanel newPanel){

        BorderLayout layout = (BorderLayout) newPanel.getLayout();
        JPanel checkPanel = (JPanel) layout.getLayoutComponent(BorderLayout.EAST);
        JCheckBox checkBox = (JCheckBox) checkPanel.getComponent(0);
        checkBox.setSelected(true);

        JPanel textPanel = (JPanel) layout.getLayoutComponent(BorderLayout.CENTER);
        JTextField key = (JTextField) textPanel.getComponent(0);
        key.setText(data[0]);
        JTextField value = (JTextField) textPanel.getComponent(1);
        value.setText(data[1]);

        updateUI();
        repaint();
        revalidate();
    }
}