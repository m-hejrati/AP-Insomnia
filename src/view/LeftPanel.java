package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * in this class we design lef part of the app
 *
 * @author Mahdi Hejarti 9723100
 * @since 2020.05.10
 */

public class LeftPanel extends JPanel{

    private JTree tree;

    /**
     * constructor for left panel
     *
     * @param controller controller object from controller class to set connection between model and view
     */
    public LeftPanel(Controller controller) {

        loadTree(controller);
    }

    /**
     * load saved request and group
     *
     * @param controller controller object from controller class to set connection between model and view
     */
    public void loadTree(Controller controller){

        DefaultMutableTreeNode request = new DefaultMutableTreeNode("Request Groups");
        tree = new JTree(request);

        tree.setBorder(new EmptyBorder(10,5,5,5));
        String[][] list = controller.loadRequestList();
        DefaultMutableTreeNode newGroup;

        for (String[] str : list) {
            newGroup = new DefaultMutableTreeNode(str[0]);
            request.add(newGroup);

            for (int i = 1; i < str.length; i++)
                if (str[i] != null)
                    newGroup.add(new DefaultMutableTreeNode(str[i]));
        }

        tree.expandRow(0);
        this.setLayout(new BorderLayout());
        this.add(tree ,BorderLayout.WEST);

        JLabel insomniaLabel = new JLabel("INSOMNIA", SwingConstants.CENTER);
        Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 5);
        insomniaLabel.setBorder(border);
        insomniaLabel.setFont (insomniaLabel.getFont ().deriveFont (20f));
        this.add(insomniaLabel, BorderLayout.NORTH);

        tree.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                TreePath path = tree.getPathForLocation(me.getX(), me.getY());
                if (path != null)
                    controller.loadRequest(path.toString());
            }
        });
    }
}