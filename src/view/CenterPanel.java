package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * in this class we design center part of the app
 *
 * @author Mahdi Hejarti 9723100
 * @since 2020.05.10
 */

public class CenterPanel extends JPanel {

    JPanel northCenter; // north part of center panel
    JTabbedPane centerCenter; // center part of center panel
    JPanel dataPanel;

    /**
     * constructor for Center Panel
     *
     * @param controller
     */
    public CenterPanel(Controller controller) {

        this.setLayout(new BorderLayout(5, 5));

        // design and add north part of the center panel
        northCenter = new JPanel(new BorderLayout(5, 5));
        northCenter.setBorder(new EmptyBorder(5, 5, 5, 5));
        String[] URLOptions = {"GET", "POST", "DELETE", "PUT", "PATCH"};
        JComboBox list = new JComboBox(URLOptions);
        list.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JComboBox list = (JComboBox) event.getSource();
                int selected = list.getSelectedIndex();
                controller.setMethod(selected, URLOptions);
            }
        });
        northCenter.add(list, BorderLayout.WEST);
        JTextField URL = new JTextField("https://www.google.com");
        URL.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                //...
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
                saveOptionPane();
                controller.saveReq();
            }
        });
        buttons.add(save);
        northCenter.add(buttons, BorderLayout.EAST);
        this.add(northCenter, BorderLayout.NORTH);


        centerCenter = new JTabbedPane();

        // design and add body panel
        ArrayList<JPanel> bodiesPanelList = new ArrayList<JPanel>();

        JPanel bodyPanel = new JPanel(new BorderLayout(5, 5));

        JPanel buttonPanel3 = new JPanel(new FlowLayout());
        JButton addButton3 = new JButton("Add");

        JPanel boxBodyPanel = new JPanel();
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
        ArrayList<JPanel> headersPanelList = new ArrayList<JPanel>();

        JPanel headerPanel = new JPanel(new BorderLayout(5, 5));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add");

        JPanel boxHeaderPanel = new JPanel();
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
//        queryField.setEditable(false);
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
//                bodyPanel.removeAll();
                controller.setBodyMethod("--data");
//                prepareDataPanel(bodyPanel);
//                bodyPanel.add(dataPanel);
//                controller
//                repaint();
//                revalidate();
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
     * this method make key value field and check bpx and button in header tab
     *
     * @param boxPanel
     * @param headersPanelList
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

    public void saveOptionPane() {

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("name:"));
        JTextField nameField = new JTextField(5);
        myPanel.add(nameField);

        myPanel.add(Box.createHorizontalStrut(15)); // a spacer

        myPanel.add(new JLabel("group:"));
        JTextField groupField = new JTextField(5);
        myPanel.add(groupField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Enter name and group to save", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            System.out.println("x value: " + nameField.getText());
            System.out.println("y value: " + groupField.getText());

        }
    }
}