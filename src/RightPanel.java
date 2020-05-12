import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

    JTabbedPane centerRight;
    JPanel northRight;

    /**
     * constructor for right panel
     */
    public RightPanel(){

        this.setLayout(new BorderLayout(5, 5));

        JPanel northRightButtons = new JPanel();
        northRightButtons.add(new JButton("code"));
        northRightButtons.add(new JButton("message"));
        northRightButtons.add(new JButton("data"));

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
                    showPopUpMenu(bodyPanel, e);
                }
            }
        });

        JPanel headerPanel = new JPanel(new BorderLayout(0 ,20));

        String[] column = {"NAME", "VALUE"};
        String[][] data = {{"Date", "Tue, 12 May 2020 12:03:26 GMT"}, {"Expires", "-1"}, {"Cache-Control", "private, max-age=0"}, {"Content-Type", "text/html; charset=ISO-8859-1"}, {"Server", "gws"}, {"X-XSS-Protection", "0"}, {"X-Frame-Options", "SAMEORIGIN"}};
        JTable headerTable = new JTable(data, column);
        headerTable.setRowHeight(20);
        headerTable.setEnabled(false);
        JScrollPane scrollTable = new JScrollPane(headerTable);
        scrollTable.setPreferredSize(new Dimension(100,200));
        headerPanel.add(scrollTable, BorderLayout.NORTH);
        headerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(new JButton("Copy to Clipboard"));
        headerPanel.add(buttonPanel, BorderLayout.CENTER);

        centerRight.add("Header", headerPanel);

        this.add(centerRight, BorderLayout.CENTER);
    }

    /**
     * this method show pop up menu when we click on Body tab
     * @param bodyPanel body panel in tab
     * @param e mouse event
     */
    public void showPopUpMenu(JPanel bodyPanel, MouseEvent e){
        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem rawItem = new JMenuItem("Raw");
        rawItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                bodyPanel.removeAll();
                bodyPanel.add(new JLabel("Raw"));
                repaint();
                revalidate();
            }
        });
        JMenuItem previewItem = new JMenuItem("Preview");
        previewItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                bodyPanel.removeAll();
                bodyPanel.add(new JLabel("Preview"));
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

        jPopupMenu.add(rawItem);
        jPopupMenu.add(previewItem);
        jPopupMenu.add(jsonItem);

        jPopupMenu.setVisible(true);
        jPopupMenu.show(bodyPanel, e.getX(), e.getY());
    }
}