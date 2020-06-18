package GUI;

import view.View;

import javax.swing.*;

/**
 * main class of project that the app run.
 *
 * @author Mahdi Hejarti 9723100
 * @since 2020.05.06
 */

public class Main1 {
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("impossible to change look and feel");
        }

        // load look and feel and theme from saved file
        View view = new View();
        int index = view.readComboItem();
        view.setTheme(index);
        view.setVisible();
    }
}