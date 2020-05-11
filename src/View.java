import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
    private boolean sidebarFlag;

    public View (){

        mainFrame = new JFrame();
        mainFrame.setTitle("Insomnia");
        mainFrame.setSize(1100, 560);
        mainFrame.setLocation(210, 170);
        mainFrame.setMinimumSize(new Dimension(600, 300));
        mainFrame.setResizable(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        leftPanel = new LeftPanel();
        JScrollPane leftScrollPane = new JScrollPane(leftPanel);

        centerPanel = new CenterPanel();
        JScrollPane centerScrollPane = new JScrollPane(centerPanel);

        rightPanel = new RightPanel();
        JScrollPane rightScrollPane = new JScrollPane(rightPanel);

        JSplitPane spRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, centerScrollPane, rightScrollPane);
        spRight.setDividerLocation(mainFrame.getSize().width * 2 / 5);
        spRight.setDividerSize(3);
        mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftScrollPane, spRight);
        mainPane.setDividerSize(2);
        mainPane.setDividerLocation(mainFrame.getSize().width / 5);
        sidebarFlag = true;

        mainFrame.add(mainPane, BorderLayout.CENTER);



        menuBar = new JMenuBar();

        JMenu appMenu = new JMenu("Application");
        appMenu.setMnemonic('A');
        JMenuItem optionItem = new JMenuItem("Option");
        optionItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        optionItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                optionFrameMethod();
            }
        });
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
        sidebarItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        sidebarItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                sidebar(leftScrollPane, centerScrollPane, rightScrollPane);
            }
        });
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
    }

    public void sidebar(JScrollPane leftScrollPane, JScrollPane centerScrollPane, JScrollPane rightScrollPane) {

        if (sidebarFlag) {
            mainPane.setLeftComponent(centerScrollPane);
            mainPane.setRightComponent(rightScrollPane);
            mainPane.setDividerSize(2);
            mainPane.setDividerLocation(mainFrame.getSize().width / 2);
            sidebarFlag  = false;
        } else {
            JSplitPane spRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, centerScrollPane, rightScrollPane);
            spRight.setDividerLocation(mainFrame.getSize().width * 2 / 5);
            spRight.setDividerSize(3);
            mainPane.setLeftComponent(leftScrollPane);
            mainPane.setRightComponent(spRight);
            mainPane.setDividerSize(2);
            mainPane.setDividerLocation(mainFrame.getSize().width / 5);
            sidebarFlag  = true;
        }
    }


    public void optionFrameMethod(){

        JFrame optionFrame = new JFrame();
        optionFrame.setTitle("Option");
        optionFrame.setSize(300, 200);
        optionFrame.setLocation(600, 300);
        optionFrame.setMinimumSize(new Dimension(250, 170));
        optionFrame.setResizable(false);
        optionFrame.setVisible(true);

        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));

        JPanel followRedirectPanel = new JPanel(new GridLayout(1,2));
//        JPanel followRedirectPanel = new JPanel(new FlowLayout());
        followRedirectPanel.setBorder(new EmptyBorder(5,5,5,5));
        followRedirectPanel.add(new JLabel("follow redirect: "));
        followRedirectPanel.add(new JCheckBox());

//        JPanel systemTrayPanel = new JPanel(new FlowLayout());
        JPanel systemTrayPanel = new JPanel(new GridLayout(1,2));
        systemTrayPanel.setBorder(new EmptyBorder(5,5,5,5));
        systemTrayPanel.add(new JLabel("System Tray: "));
        systemTrayPanel.add(new JCheckBox());

//        JPanel themePanel = new JPanel(new FlowLayout());
        JPanel themePanel = new JPanel(new GridLayout(1,2));
        themePanel.setBorder(new EmptyBorder(5,5,5,5));
        themePanel.add(new JLabel("Theme: "));
        String[] theme = {"light theme", "dark theme"};
        JComboBox themeCombo = new JComboBox(theme);
        themePanel.add(themeCombo);

        optionPanel.add(followRedirectPanel);
        optionPanel.add(systemTrayPanel);
        optionPanel.add(themePanel);

        optionFrame.add(optionPanel, BorderLayout.NORTH);

    }

    /**
     * set the frame visible to show
     */
    public void setVisible() {
        mainFrame.setVisible(true);
    }
}
