import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RightPanel extends JPanel{

    public RightPanel(){

        this.setLayout(new BorderLayout(5, 5));
//        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel northRight = new JPanel();
        northRight.add(new JButton("code"));
        northRight.add(new JButton("message"));
        northRight.add(new JButton("data"));
        this.add(northRight, BorderLayout.NORTH);



        JTabbedPane centerRight = new JTabbedPane();

        centerRight.add("message body", new JPanel());

        JPanel textPanel1 = new JPanel(new GridLayout(1,2));
        textPanel1.setBorder(new EmptyBorder(5,5,5,5));
        JTextArea keyTextField1 = new JTextArea();
        keyTextField1.setEditable(false);
        keyTextField1.setText("key1");
        textPanel1.add(keyTextField1);
        JTextArea valueTextField1 = new JTextArea();
        valueTextField1.setEditable(false);
        valueTextField1.setText("value1");
        textPanel1.add(valueTextField1);

        JPanel textPanel2 = new JPanel(new GridLayout(1,2));
        textPanel2.setBorder(new EmptyBorder(5,5,5,5));
        JTextArea keyTextField2 = new JTextArea();
        keyTextField2.setEditable(false);
        keyTextField2.setText("key2");
        textPanel2.add(keyTextField2);
        JTextArea valueTextField2 = new JTextArea();
        valueTextField2.setEditable(false);
        valueTextField2.setText("value2");
        textPanel2.add(valueTextField2);

        JPanel boxHeaderPanel = new JPanel();
        boxHeaderPanel.setLayout(new BoxLayout(boxHeaderPanel, BoxLayout.Y_AXIS));
        boxHeaderPanel.add(textPanel1);
        boxHeaderPanel.add(textPanel2);

        JPanel headerPanel = new JPanel(new BorderLayout(5,5));
        headerPanel.add(boxHeaderPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(new JButton("Copy"));
        headerPanel.add(buttonPanel, BorderLayout.CENTER);

        centerRight.add("Header", headerPanel);
        centerRight.add("Auth", new JPanel());

        this.add(centerRight, BorderLayout.CENTER);
    }
}