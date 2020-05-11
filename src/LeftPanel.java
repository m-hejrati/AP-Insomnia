import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class LeftPanel extends JPanel{

    private JTree tree;

    public LeftPanel() {

        DefaultMutableTreeNode request = new DefaultMutableTreeNode("Request List");
        tree = new JTree(request);
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
        this.add(tree);
    }
}