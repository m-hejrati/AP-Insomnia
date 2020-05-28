import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

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
     */
    public LeftPanel() {

        DefaultMutableTreeNode request = new DefaultMutableTreeNode("Request List");
        tree = new JTree(request);

        tree.setBorder(new EmptyBorder(10,5,5,5));
        DefaultMutableTreeNode first = new DefaultMutableTreeNode("first group");
        DefaultMutableTreeNode second = new DefaultMutableTreeNode("second group");
        DefaultMutableTreeNode third = new DefaultMutableTreeNode("third group");

        request.add(first);
        request.add(second);
        request.add(third);
        DefaultMutableTreeNode first1 = new DefaultMutableTreeNode("first req");
        DefaultMutableTreeNode first2 = new DefaultMutableTreeNode("second req");
        first.add(first1);
        first.add(first2);
        DefaultMutableTreeNode second1 = new DefaultMutableTreeNode("first req");
        second.add(second1);
        DefaultMutableTreeNode third1 = new DefaultMutableTreeNode("first req");
        third.add(third1);
        tree.expandRow(0);
//        tree.setBackground(new Color(238,238,238));
        this.setLayout(new BorderLayout());
        this.add(tree ,BorderLayout.WEST);

        JLabel insomniaLabel = new JLabel("INSOMNIA", SwingConstants.CENTER);
        Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 5);
        insomniaLabel.setBorder(border);
        insomniaLabel.setFont (insomniaLabel.getFont ().deriveFont (20f));
        this.add(insomniaLabel, BorderLayout.NORTH);
    }
}