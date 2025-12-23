package aplicacao;

import javax.swing.SwingUtilities;
import visao.BibliotecaApp;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BibliotecaApp().setVisible(true);
        });
    }
}
