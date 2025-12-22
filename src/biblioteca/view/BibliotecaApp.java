package biblioteca.view;

import javax.swing.*;
import java.awt.*;

public class BibliotecaApp extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    private InserirLivroPanel inserirLivroPanel = new InserirLivroPanel();
    private ListarLivrosPanel listarLivrosPanel = new ListarLivrosPanel();

    public BibliotecaApp() {
        setTitle("Biblioteca - Etapa 3");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel.add(inserirLivroPanel, "inserir");
        mainPanel.add(listarLivrosPanel, "listar");

        add(mainPanel);

        inserirLivroPanel.setListener(() -> {
            listarLivrosPanel.atualizarTabela();
            cardLayout.show(mainPanel, "listar");
        });

        listarLivrosPanel.setListener(() -> {
            cardLayout.show(mainPanel, "inserir");
        });

        cardLayout.show(mainPanel, "inserir");
    }
}