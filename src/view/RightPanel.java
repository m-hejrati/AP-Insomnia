package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * in this class we design right part of the app
 *
 * @author Mahdi Hejarti 9723100
 * @since 2020.05.10
 */

public class RightPanel extends JPanel{

    private JTabbedPane centerRight;
    private JPanel northRight;
    private JButton statusCodeButton;
    private JButton statusMessageButton;
    private JButton timeButton;
    private JTable headerTable;

    /**
     * constructor for right panel
     * @param controller controller object from controller class to set connection between model and view
     */
    public RightPanel(Controller controller){

        this.setLayout(new BorderLayout(5, 5));

        JPanel northRightButtons = new JPanel();
        statusCodeButton = new JButton("code");
        northRightButtons.add(statusCodeButton);

        statusMessageButton = new JButton("message");
        northRightButtons.add(statusMessageButton);

        timeButton = new JButton("time");
        northRightButtons.add(timeButton);

        northRight = new JPanel(new BorderLayout(5, 5));
        northRight.add(northRightButtons, BorderLayout.WEST);

        String[] URLOptions = {"Just Now", "Today"};
        JComboBox list = new JComboBox(URLOptions);
        northRight.add(list, BorderLayout.EAST);

        this.add(northRight, BorderLayout.NORTH);

        centerRight = new JTabbedPane();
        JPanel bodyPanel = new JPanel();
        centerRight.add("Message Body", bodyPanel);
        JPanel tabComponentPanel = new JPanel();
        tabComponentPanel.add(new JLabel("Message Body"));
        centerRight.setTabComponentAt(0, tabComponentPanel);
        tabComponentPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    centerRight.setSelectedComponent(bodyPanel);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    showPopUpMenu(bodyPanel, e, controller);
                }
            }
        });

        JPanel headerPanel = new JPanel(new BorderLayout(0 ,20));

        String[] column = {"NAME", "VALUE"};
        String[][] data = {{"key1", "value1"}};
        DefaultTableModel model = new DefaultTableModel(data, column);
        headerTable = new JTable(model);
        headerTable.setRowHeight(20);
        headerTable.setEnabled(false);

        JScrollPane scrollTable = new JScrollPane(headerTable);
        scrollTable.setPreferredSize(new Dimension(100,300));
        headerPanel.add(scrollTable, BorderLayout.NORTH);
        headerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton copyButton = new JButton("Copy to Clipboard");
        copyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                controller.copyToClipboard();
                copyButton.setText("Copied");
            }
        });
        buttonPanel.add(copyButton);
        headerPanel.add(buttonPanel, BorderLayout.CENTER);

        centerRight.add("Header", headerPanel);

        this.add(centerRight, BorderLayout.CENTER);

    }

    /**
     * this method show pop up menu when we click on Body tab
     * @param bodyPanel body panel in tab
     * @param e mouse event
     * @param controller controller object from controller class to set connection between model and view
     */
    public void showPopUpMenu(JPanel bodyPanel, MouseEvent e, Controller controller){

        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem rawItem = new JMenuItem("Raw");
        rawItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                bodyPanel.removeAll();

                JTextArea raw = new JTextArea();
                raw.setEditable(false);
                controller.rawResponse(raw);
                bodyPanel.add(new JScrollPane(raw));

                repaint();
                revalidate();
            }
        });

        JMenuItem previewItem = new JMenuItem("Preview");
        previewItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                bodyPanel.removeAll();
                controller.previewResponse(bodyPanel);
                repaint();
                revalidate();
            }
        });

//        JMenuItem jsonItem = new JMenuItem("JSON");
//        jsonItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ev) {
//                bodyPanel.removeAll();
//                bodyPanel.add(new JLabel("JSON"));
//                repaint();
//                revalidate();
//            }
//        });

        jPopupMenu.add(rawItem);
        jPopupMenu.add(previewItem);
//        jPopupMenu.add(jsonItem);

        jPopupMenu.setVisible(true);
        jPopupMenu.show(bodyPanel, e.getX(), e.getY());
    }

    /**
     * show status code
     * @param code status code
     */
    public void setStatusCodeButton(int code) {
        statusCodeButton.setText(String.valueOf(code));
    }

    /**
     * show status message
     * @param message status message
     */
    public void setStatusMessageButton(String message) {
        statusMessageButton.setText(message);
    }

    /**
     * show time
     * @param time time
     */
    public void setTimeButton(String time) {
        timeButton.setText(time);
    }

    /**
     * remove headers in panel
     */
    public void removeHeader(){

        DefaultTableModel model = (DefaultTableModel) headerTable.getModel();
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--)
            model.removeRow(i);

    }

    /**
     * show headers
     * @param data data
     */
    public void addHeaderTable(Object[] data) {

        DefaultTableModel model = (DefaultTableModel) headerTable.getModel();
        model.addRow(data);
    }

}