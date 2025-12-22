package biblioteca;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new biblioteca.view.BibliotecaApp().setVisible(true);
        });
    }
}