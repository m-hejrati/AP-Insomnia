import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CenterPanel extends JPanel{

    public CenterPanel() {

        this.setLayout(new BorderLayout(5, 10));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel northCenter = new JPanel(new BorderLayout(5, 5));
        String[] URLOptions = {"PATCH", "PUT", "POST", "DELETE", "GET"};
        JComboBox list = new JComboBox(URLOptions);
        northCenter.add(list, BorderLayout.WEST);
        northCenter.add(new JTextField(), BorderLayout.CENTER);
        JPanel buttons = new JPanel(new GridLayout(1, 2, 5, 5));
        buttons.add(new JButton("Send"));
        buttons.add(new JButton("Save"));
        northCenter.add(buttons, BorderLayout.EAST);
        this.add(northCenter, BorderLayout.NORTH);



        JTabbedPane centerCenter = new JTabbedPane();

//        String bodyOptions[] = {"Form Data","JSON","Binary Data"};
//        JComboBox boyList = new JComboBox(bodyOptions);
        centerCenter.add("bodyList", new JPanel());

        JPanel textPanel = new JPanel(new GridLayout(1, 2));
        textPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
        textPanel.add(new JTextField());
        textPanel.add(new JTextField());
        JPanel checkPanel = new JPanel(new GridLayout(1, 2));
        checkPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
        checkPanel.add(new JCheckBox());
        checkPanel.add(new Button("*"));

        JPanel keyValuePanel = new JPanel(new BorderLayout(5, 5));
        keyValuePanel.add(textPanel, BorderLayout.CENTER);
        keyValuePanel.add(checkPanel, BorderLayout.EAST);


        JPanel textPanel2 = new JPanel(new GridLayout(1,2));
        textPanel2.setBorder(new EmptyBorder(5,0,0,0));
        textPanel2.add(new JTextField());
        textPanel2.add(new JTextField());
        JPanel checkPanel2 = new JPanel(new GridLayout(1,2));
        checkPanel2.setBorder(new EmptyBorder(5,0,0,0));
        checkPanel2.add(new JCheckBox());
        checkPanel2.add(new Button("*"));
        JPanel keyValuePanel2 = new JPanel(new BorderLayout(5, 5));
        keyValuePanel2.add(textPanel2, BorderLayout.CENTER);
        keyValuePanel2.add(checkPanel2, BorderLayout.EAST);

        JPanel boxHeaderPanel = new JPanel();
        boxHeaderPanel.setLayout(new BoxLayout(boxHeaderPanel, BoxLayout.Y_AXIS));
        boxHeaderPanel.add(keyValuePanel);
        boxHeaderPanel.add(keyValuePanel2);

        JPanel headerPanel = new JPanel(new BorderLayout(5, 5));
        headerPanel.add(boxHeaderPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(new JButton("Add"));
        headerPanel.add(buttonPanel, BorderLayout.CENTER);

        centerCenter.add("Header", headerPanel);
        centerCenter.add("Query", new JPanel());
        centerCenter.add("Auth", new JPanel());

        this.add(centerCenter, BorderLayout.CENTER);
    }
}