import javax.imageio.plugins.tiff.GeoTIFFTagSet;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class View {

    private JFrame mainFrame;
    private JSplitPane mainPane;
    private JPanel leftPanel;
    private JPanel centerPanel;
    private JPanel rightPanel;
    private JMenuBar menuBar;
    private JTree tree;

    public View (){

        mainFrame = new JFrame();
        mainFrame.setTitle("Insomnia");
        mainFrame.setSize(1100, 560);
        mainFrame.setSize(1100, 560);
        mainFrame.setLocation(210, 170);
//        mainFrame.setMinimumSize(new Dimension(380, 350));
        mainFrame.setResizable(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //------------------------------------------------------------------------------------------------------------


        //------------------------------------------------------------------------------------------------------------
        leftPanel = new JPanel();
//        leftPanel.setBackground(new Color(30, 30, 30));
        JScrollPane leftScrollPane = new JScrollPane(leftPanel);
        centerPanel = new JPanel(new BorderLayout(5, 10));
        centerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
//        centerPanel.setBackground(new Color(30, 30, 30));
        JScrollPane centerScrollPane = new JScrollPane(centerPanel);
        rightPanel = new JPanel();
//        rightPanel.setBackground(new Color(30, 30, 30));
        JScrollPane rightScrollPane = new JScrollPane(rightPanel);

        JSplitPane spRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, centerScrollPane, rightScrollPane);
        spRight.setDividerLocation(mainFrame.getSize().width / 3);
        spRight.setDividerSize(3);

        mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftScrollPane, spRight);
        mainPane.setDividerSize(2);
        mainPane.setDividerLocation(mainFrame.getSize().width / 4);
        mainFrame.add(mainPane, BorderLayout.CENTER);

        //------------------------------------------------------------------------------------------------------------
        DefaultMutableTreeNode request = new DefaultMutableTreeNode("Request List");
        tree = new JTree(request);
//        jt.setBackground(new Color(150,150,150));
        DefaultMutableTreeNode first = new DefaultMutableTreeNode("first group");
        DefaultMutableTreeNode second = new DefaultMutableTreeNode("second group");
        request.add(first);
        request.add(second);
        DefaultMutableTreeNode first1 = new DefaultMutableTreeNode("first req");
        DefaultMutableTreeNode first2 = new DefaultMutableTreeNode("second req");
        first.add(first1);
        first.add(first2);
        DefaultMutableTreeNode second1 = new DefaultMutableTreeNode("first req");
        second.add(second1);
        tree.expandRow(0);
        leftPanel.add(tree);
        //------------------------------------------------------------------------------------------------------------

        //------------------------------------------------------------------------------------------------------------
        JPanel northCenter = new JPanel(new BorderLayout(5,5));
        centerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        String URLOptions[] = {"PATCH","PUT","POST","DELETE","GET"};
        JComboBox list = new JComboBox(URLOptions);
        northCenter.add(list, BorderLayout.WEST);
        northCenter.add(new JTextField(), BorderLayout.CENTER);
        northCenter.add(new JButton("Send"), BorderLayout.EAST);
        centerPanel.add(northCenter, BorderLayout.NORTH);
        //------------------------------------------------------------------------------------------------------------

        JTabbedPane centerCenter = new JTabbedPane();

//        String bodyOptions[] = {"Form Data","JSON","Binary Data"};
//        JComboBox boyList = new JComboBox(bodyOptions);
        centerCenter.add("bodyList", new JPanel());

        JPanel textPanel = new JPanel(new GridLayout(1,2));
        textPanel.setBorder(new EmptyBorder(5,0,0,0));
        textPanel.add(new JTextField());
        textPanel.add(new JTextField());
        JPanel checkPanel = new JPanel(new GridLayout(1,2));
        checkPanel.setBorder(new EmptyBorder(5,0,0,0));
        checkPanel.add(new JCheckBox());
        checkPanel.add(new Button("*"));

        JPanel keyValuePanel = new JPanel(new BorderLayout(5, 5));
        keyValuePanel.add(textPanel, BorderLayout.CENTER);
        keyValuePanel.add(checkPanel, BorderLayout.EAST);

        JPanel boxHeaderPanel = new JPanel();
        boxHeaderPanel.setLayout(new BoxLayout(boxHeaderPanel, BoxLayout.Y_AXIS));
        boxHeaderPanel.add(keyValuePanel);

        JPanel headerPanel = new JPanel(new BorderLayout(5,5));
        headerPanel.add(boxHeaderPanel, BorderLayout.NORTH);

        centerCenter.add("Header", headerPanel);
        centerCenter.add("Query", new JPanel());
        centerCenter.add("Auth", new JPanel());

        centerPanel.add(centerCenter, BorderLayout.CENTER);

        //------------------------------------------------------------------------------------------------------------
        menuBar = new JMenuBar();

        JMenu appMenu = new JMenu("Application");
        appMenu.setMnemonic('A');
        JMenuItem optionItem = new JMenuItem("Option");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                System.exit(0);
            }
        });

        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic('V');
        JMenuItem sidebarItem = new JMenuItem("Toggle Sidebar");
        JMenuItem fullScreenItem = new JMenuItem("Toggle Full Screen");
        fullScreenItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        fullScreenItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                mainFrame.setExtendedState((mainFrame.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH ? JFrame.NORMAL : JFrame.MAXIMIZED_BOTH);
            }
        });

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                JOptionPane.showMessageDialog(null,
                        "Mahdi Hejrati \n9723100 \n",
                        "About",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        JMenuItem helpItem = new JMenuItem("Help");
        helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        helpItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                JOptionPane.showMessageDialog(null,
                        "Empty",
                        "Help",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });

        appMenu.add(optionItem);
        appMenu.add(exitItem);
        viewMenu.add(sidebarItem);
        viewMenu.add(fullScreenItem);
        helpMenu.add(aboutItem);
        helpMenu.add(helpItem);
        menuBar.add(appMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        mainFrame.setJMenuBar(menuBar);
        //-------------------------------------------------------------------------------

    }


    /**
     * set the frame visible to show
     */
    public void setVisible() {
        mainFrame.setVisible(true);
    }
}
