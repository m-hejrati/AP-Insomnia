package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * view class in mvc design pattern.
 * this app contains of three parts.
 *
 * @author Mahdi Hejarti 9723100
 * @since 2020.05.06
 */

public class View {

    private JFrame mainFrame;
    private JSplitPane mainPane;
    private LeftPanel leftPanel;
    private CenterPanel centerPanel;
    private RightPanel rightPanel;
    private JMenuBar menuBar;
    private boolean sidebarFlag;

    private Controller controller;

    /**
     * constructor for view class
     */
    public View() {

        controller = new Controller(this);

        mainFrame = new JFrame();
        mainFrame.setTitle("Insomnia");
        mainFrame.setSize(1100, 560);
        mainFrame.setLocation(210, 170);
        mainFrame.setMinimumSize(new Dimension(600, 300));
        mainFrame.setResizable(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        leftPanel = new LeftPanel(controller);
        JScrollPane leftScrollPane = new JScrollPane(leftPanel);

        centerPanel = new CenterPanel(controller);
        JScrollPane centerScrollPane = new JScrollPane(centerPanel);

        rightPanel = new RightPanel(controller);
//        JScrollPane rightScrollPane = new JScrollPane(rightPanel);
        rightPanel.setBorder(new EmptyBorder(3,0,0,5));

        JSplitPane spRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, centerScrollPane, rightPanel);
        spRight.setDividerLocation(mainFrame.getSize().width * 2 / 5);
        spRight.setDividerSize(3);
        mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftScrollPane, spRight);
        mainPane.setDividerSize(2);
        mainPane.setDividerLocation(mainFrame.getSize().width / 5);
        sidebarFlag = true;

        mainFrame.add(mainPane, BorderLayout.CENTER);

        // design menu bar
        menuBar = new JMenuBar();

        // add application menu
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
                int index = readTrayItem();
                if (index == 1) {
                    mainFrame.setVisible(false);
                    systemTray();
                } else
                    System.exit(0);
            }
        });

        // add view menu
        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic('V');
        JMenuItem sidebarItem = new JMenuItem("Toggle Sidebar");
        sidebarItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        sidebarItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                sidebar(leftScrollPane, centerScrollPane, rightPanel);
            }
        });

        JMenuItem fullScreenItem = new JMenuItem("Toggle Full Screen");
        fullScreenItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        fullScreenItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                mainFrame.setExtendedState((mainFrame.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH ? JFrame.NORMAL : JFrame.MAXIMIZED_BOTH);
            }
        });

        // add help menu
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
                        helpMessage(),
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

    /**
     * this method toggle the sidebar
     *  @param leftScrollPane   left panel
     * @param centerScrollPane center panel
     * @param rightPanel  right panel
     */
    public void sidebar(JScrollPane leftScrollPane, JScrollPane centerScrollPane, RightPanel rightPanel) {

        if (sidebarFlag) {
            mainPane.setLeftComponent(centerScrollPane);
            mainPane.setRightComponent(rightPanel);
            mainPane.setDividerSize(2);
            mainPane.setDividerLocation(mainFrame.getSize().width / 2);
            sidebarFlag = false;

        } else {
            JSplitPane spRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, centerScrollPane, rightPanel);
            spRight.setDividerLocation(mainFrame.getSize().width * 2 / 5);
            spRight.setDividerSize(3);
            mainPane.setLeftComponent(leftScrollPane);
            mainPane.setRightComponent(spRight);
            mainPane.setDividerSize(2);
            mainPane.setDividerLocation(mainFrame.getSize().width / 5);
            sidebarFlag = true;
        }
    }

    /**
     * this method show the option frame
     */
    public void optionFrameMethod() {

        JFrame optionFrame = new JFrame();
        optionFrame.setTitle("Option");
        optionFrame.setSize(300, 200);
        optionFrame.setLocation(600, 300);
        optionFrame.setMinimumSize(new Dimension(250, 170));
        optionFrame.setResizable(false);
        optionFrame.setVisible(true);

        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));

        JPanel followRedirectPanel = new JPanel(new GridLayout(1, 2));
        followRedirectPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        followRedirectPanel.add(new JLabel("follow redirect: "));
        followRedirectPanel.add(new JCheckBox());

        JPanel systemTrayPanel = new JPanel(new GridLayout(1, 2));
        systemTrayPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        systemTrayPanel.add(new JLabel("System Tray: "));
        JCheckBox trayBox = new JCheckBox();
        systemTrayPanel.add(trayBox);
        int trayIndex = readTrayItem();
        if (trayIndex == 1)
            trayBox.setSelected(true);
        else
            trayBox.setSelected(false);
        trayBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JCheckBox tray = (JCheckBox) event.getSource();
                if (tray.isSelected())
                    writeTrayItem(1);
                else
                    writeTrayItem(0);
            }
        });

        JPanel themePanel = new JPanel(new GridLayout(1, 2));
        themePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        themePanel.add(new JLabel("Theme: "));
        String[] theme = {"light theme", "dark theme"};
        JComboBox themeCombo = new JComboBox(theme);
        themePanel.add(themeCombo);
        int index = readComboItem();
        try {
            themeCombo.setSelectedIndex(index);
        } catch (Exception e) {
            System.err.println("Invalid value in file");
        }
        themeCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JComboBox combo = (JComboBox) event.getSource();
                int selected = combo.getSelectedIndex();
                writeComboItem(selected);
                setTheme(selected);
                SwingUtilities.updateComponentTreeUI(mainFrame);
                SwingUtilities.updateComponentTreeUI(optionFrame);
            }
        });

        optionPanel.add(followRedirectPanel);
        optionPanel.add(systemTrayPanel);
        optionPanel.add(themePanel);

        optionFrame.add(optionPanel, BorderLayout.NORTH);

    }

    /**
     * this method read index of combo box from saved file
     *
     * @return index of combo box
     */
    public int readComboItem() {

        try (FileReader fileReader = new FileReader("./files/combo.txt")) {
            return fileReader.read() - 48;
        } catch (Exception e) {
            System.err.println("Error in reading file");
        }
        return 0;
    }

    /**
     * this method save changed index of combo box in the file
     *
     * @param index index to save
     */
    public void writeComboItem(int index) {

        try (FileWriter fileWriter = new FileWriter("./files/combo.txt")) {
            fileWriter.write(index + 48);
        } catch (Exception e) {
            System.err.println("Error in writing file");
        }
    }

    /**
     * set theme between light and dark
     *
     * @param index index of theme in combo box, 0 light, 1 dark
     */
    public void setTheme(int index) {
        switch (index) {
            case 0:
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                    UIManager.put("nimbusLightBackground", new Color(240, 240, 240));
                    UIManager.put("control", new Color(214, 217, 223));
                    UIManager.put("nimbusBase", new Color(51, 98, 140));
                    UIManager.put("nimbusFocus", new Color(115, 164, 209));
                    UIManager.put("nimbusLightBackground", new Color(240, 240, 240));
                    UIManager.put("text", new Color(0, 0, 0));
                } catch (Exception e) {
                    System.err.println("impossible to change theme");
                }
                break;
            case 1:
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                    UIManager.put("control", new Color(34, 34, 34));
                    UIManager.put("nimbusBase", new Color(0, 0, 0));
                    UIManager.put("nimbusFocus", new Color(0, 0, 0));
                    UIManager.put("nimbusLightBackground", new Color(44, 44, 44));
                    UIManager.put("nimbusSelectionBackground", new Color(74, 74, 74));
                    UIManager.put("text", new Color(255, 255, 255));
                } catch (Exception e) {
                    System.err.println("impossible to change theme");
                }
                break;
        }
    }

    /**
     * this method read index of tray box from saved file
     *
     * @return index of combo box
     */
    public int readTrayItem() {
        try (FileReader fileReader = new FileReader("./files/tray.txt")) {
            return fileReader.read() - 48;
        } catch (Exception e) {
            System.err.println("Error in reading file");
        }
        return 0;
    }

    /**
     * this method save changed index of tray box in the file
     *
     * @param index index to save
     */
    public void writeTrayItem(int index) {
        try (FileWriter fileWriter = new FileWriter("./files/tray.txt")) {
            fileWriter.write(index + 48);
        } catch (Exception e) {
            System.err.println("Error in writing file");
        }
    }

    /**
     * hide app in system tray
     */
    public void systemTray() {

        if (SystemTray.isSupported()) {

            SystemTray systemTray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage("./files/java.png");

            PopupMenu trayPopupMenu = new PopupMenu();

            TrayIcon trayIcon = new TrayIcon(image, "Java MidTerm Project", trayPopupMenu);
            trayIcon.setImageAutoSize(true);

            try {
                systemTray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println("Impossible to hide in system tray");
            }

            MenuItem open = new MenuItem("Open");
            open.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mainFrame.setVisible(true);
                    mainFrame.setExtendedState(JFrame.NORMAL);
                    systemTray.remove(trayIcon);
                }
            });
            trayPopupMenu.add(open);

            MenuItem close = new MenuItem("Close");
            close.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            trayPopupMenu.add(close);

        } else {
            System.out.println("System tray is not supported!");
        }
    }

    public String helpMessage() {

        return ("Help: "
                + "\n-u <>, --url <> \t : set url \t\t\t\t\t\t\t\t ,[--url http://www.google.com]"
                + "\n-i   , \t\t\t\t : show response headers"
                + "\n-M <>, --method <> \t : set method \t\t\t\t\t\t\t ,[--method GET]"
                + "\n-H <>, --header <> \t : set headers \t\t\t\t\t\t\t ,[--header \"key1:value1;key2:value2\"]"
                + "\n-d <>, --data <> \t : set message body in form-data \t\t ,[--data \"key1=value1&key2=value2\"]"
                + "\n     , --upload <> \t : set message body with uploaded file \t ,[--upload E:\\MidTermTest\\note.txt]"
                + "\n-O <>, --output <> \t : save response body in entered file \t ,[--output E:\\MidTermTest\\note.png]" +
                "\n \t\t\t\t\t   if you not enter file name, it will save in file with default name"
                + "\n     , --create <> \t : create a new request group \t\t\t ,[--create group1]"
                + "\n-S <>, --save <> \t : save a request in the group \t\t\t ,[--save req1 group1]"
                + "\n     , --list <> \t : show saved requests in the group \t ,[--list group1]" +
                "\n \t\t\t\t\t   if you not enter group request name, it will show all the groups"
                + "\n     , --fire <> \t : send a saved request from a group\t ,[--fire group1 req1]");
    }

    /**
     * set the frame visible to show
     */
    public void setVisible() {
        mainFrame.setVisible(true);
    }

    public RightPanel getRightPanel() {
        return rightPanel;
    }

    public LeftPanel getLeftPanel() {
        return leftPanel;
    }

    public CenterPanel getCenterPanel() {
        return centerPanel;
    }
}