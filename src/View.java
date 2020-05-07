import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class View {

    private JFrame mainFrame;
    private JSplitPane mainPane;
    private JPanel left;
    private JPanel center;
    private JPanel right;
    private JMenuBar menuBar;

    public View (){

        mainFrame = new JFrame();
        mainFrame.setTitle("Insomnia");
        mainFrame.setSize(1100, 560);
        mainFrame.setLocation(210, 170);
//        mainFrame.setMinimumSize(new Dimension(380, 350));
        mainFrame.setResizable(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        left = new JPanel();
        left.setBackground(new Color(30, 30, 30));
        center = new JPanel();
        center.setBackground(new Color(30, 30, 30));
        right = new JPanel();
        right.setBackground(new Color(30, 30, 30));


        JSplitPane spRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, center, right);
        spRight.setDividerLocation(mainFrame.getSize().width / 3);
        spRight.setDividerSize(3);

        mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, spRight);
        mainPane.setDividerSize(2);
        mainPane.setDividerLocation(mainFrame.getSize().width / 4);
        mainFrame.add(mainPane, BorderLayout.CENTER);



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
        sidebarItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        sidebarItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {

            }
        });        JMenuItem fullScreenItem = new JMenuItem("Toggle Full Screen");
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


    /**
     * set the frame visible to show
     */
    public void setVisible() {
        mainFrame.setVisible(true);
    }
}
