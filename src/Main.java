import javax.swing.*;

/**
 * main class of project that the app run.
 *
 * @author Mahdi Hejarti 9723100
 * @since 2020.05.06
 */

public class Main {
    public static void main(String[] args) {

        View view = new View();
        view.setVisible();

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            // Nothing to do ...
        }

    }
}