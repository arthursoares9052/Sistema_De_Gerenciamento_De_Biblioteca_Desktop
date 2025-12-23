package visao;

import javax.swing.*;
import java.awt.*;
import servicos.LivroService;

public class BibliotecaApp extends JFrame {

    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    private final LivroService service = new LivroService();

    private final InserirLivroPanel inserirLivroPanel;
    private ListarLivrosPanel listarLivrosPanel;

    public BibliotecaApp() {
        setTitle("Biblioteca");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inserirLivroPanel = new InserirLivroPanel(service);
        listarLivrosPanel = new ListarLivrosPanel(service);

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
