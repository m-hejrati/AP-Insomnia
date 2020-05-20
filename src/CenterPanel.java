import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;

/**
 * in this class we design center part of the app
 *
 * @author Mahdi Hejarti 9723100
 * @since 2020.05.10
 */

public class CenterPanel extends JPanel{

    JPanel northCenter; // north part of center panel
    JTabbedPane centerCenter; // center part of center panel

    /**
     * constructor for Center Panel
     */
    public CenterPanel() {

        this.setLayout(new BorderLayout(5, 5));

        // design and add north part of the center panel
        northCenter = new JPanel(new BorderLayout(5, 5));
        northCenter.setBorder(new EmptyBorder(5,5,5,5));
        String[] URLOptions = {"GET", "DELETE", "POST","PUT", "PATCH"};
        JComboBox list = new JComboBox(URLOptions);
        northCenter.add(list, BorderLayout.WEST);
        northCenter.add(new JTextField("https://www.google.com"), BorderLayout.CENTER);
        JPanel buttons = new JPanel(new GridLayout(1, 2, 5, 5));
        buttons.add(new JButton("Send"));
        buttons.add(new JButton("Save"));
        northCenter.add(buttons, BorderLayout.EAST);
        this.add(northCenter, BorderLayout.NORTH);


        centerCenter = new JTabbedPane();

        // design and add body panel
        JPanel bodyPanel = new JPanel();
        centerCenter.add("Body", bodyPanel);
        JPanel tabComponentPanel = new JPanel();
        tabComponentPanel.add(new JLabel("Body"));
        centerCenter.setTabComponentAt(0, tabComponentPanel);
        tabComponentPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    centerCenter.setSelectedComponent(bodyPanel);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    showPopUpMenu(bodyPanel, e);
                }
            }
        });


        // design and add header panel
        JPanel headerPanel = new JPanel(new BorderLayout(5, 5));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add");

        JPanel boxHeaderPanel = new JPanel();
        boxHeaderPanel.setLayout(new BoxLayout(boxHeaderPanel, BoxLayout.Y_AXIS));
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                boxHeaderPanel.add(addHeaderField());
                repaint();
                revalidate();
            }
        });
        buttonPanel.add(addButton);
        headerPanel.add(buttonPanel, BorderLayout.CENTER);
        headerPanel.add(boxHeaderPanel, BorderLayout.NORTH);
        centerCenter.add("Header", headerPanel);

        // design query panel
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
                boxQueryPanel.add(addHeaderField());
                repaint();
                revalidate();
            }
        });
        buttonPanel2.add(addButton2);
        queryPanel.add(buttonPanel2, BorderLayout.CENTER);
        queryPanel.add(boxQueryPanel, BorderLayout.NORTH);
        centerCenter.add("Query", queryPanel);


        // design auth panel
        JPanel authPanel = new JPanel();
        authPanel.setLayout(new BoxLayout(authPanel, BoxLayout.Y_AXIS));

        JPanel tokenPanel = new JPanel(new FlowLayout());
        tokenPanel.setBorder(new EmptyBorder(5,2,2,5));
        tokenPanel.add(new JLabel("TOKEN:         "));
        JTextField firstField = new JTextField();
        firstField.setPreferredSize(new Dimension(300,20));
        tokenPanel.add(firstField);

        JPanel prefixPanel = new JPanel(new FlowLayout());
        prefixPanel.setBorder(new EmptyBorder(5,2,2, 5));
        prefixPanel.add(new JLabel("PREFIX:         "));
        JTextField secondField = new JTextField();
        secondField.setPreferredSize(new Dimension(300,20));
        prefixPanel.add(secondField);

        JPanel enablePanel = new JPanel(new GridLayout(1,2));
        enablePanel.setBorder(new EmptyBorder(10,2,2,5));
        enablePanel.add(new JLabel("       ENABLE:"));
        enablePanel.add(new JCheckBox());

        authPanel.add(tokenPanel);
        authPanel.add(prefixPanel);
        authPanel.add(enablePanel);

        JPanel authPanel2 = new JPanel(new BorderLayout());
        authPanel2.add(authPanel, BorderLayout.NORTH);
        centerCenter.add("Auth",authPanel2);

        this.add(centerCenter, BorderLayout.CENTER);
    }

    /**
     * this method show pop up menu when we click on Body tab
     * @param bodyPanel body panel in tab
     * @param e mouse event
     */
    public void showPopUpMenu(JPanel bodyPanel, MouseEvent e){
        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem dataItem = new JMenuItem("Form Data");
        dataItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                bodyPanel.removeAll();
                bodyPanel.add(new JLabel("Form Data"));
                repaint();
                revalidate();
            }
        });
        JMenuItem jsonItem = new JMenuItem("JSON");
        jsonItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                bodyPanel.removeAll();
                bodyPanel.add(new JLabel("JSON"));
                repaint();
                revalidate();
            }
        });
        JMenuItem binaryItem = new JMenuItem("Binary Data");
        binaryItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                j.showOpenDialog(null);
            }
        });

        jPopupMenu.add(dataItem);
        jPopupMenu.add(jsonItem);
        jPopupMenu.add(binaryItem);

        jPopupMenu.setVisible(true);
        jPopupMenu.show(bodyPanel, e.getX(), e.getY());
    }

    /**
     * this method make key value field and check bpx and button in header tab
     * @return created panel
     */
    public JPanel addHeaderField(){

        JPanel textPanel = new JPanel(new GridLayout(1, 2));
        textPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
        textPanel.add(new JTextField("name"));
        textPanel.add(new JTextField("value"));
        JPanel checkPanel = new JPanel(new GridLayout(1, 2));
        checkPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
        checkPanel.add(new JCheckBox());
        checkPanel.add(new JButton("\u2718"));
        JPanel keyValuePanel = new JPanel(new BorderLayout(5, 5));
        keyValuePanel.add(textPanel, BorderLayout.CENTER);
        keyValuePanel.add(checkPanel, BorderLayout.EAST);

        return keyValuePanel;
    }
}