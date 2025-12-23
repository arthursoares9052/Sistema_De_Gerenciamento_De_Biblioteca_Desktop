package visao;

import modelo.Livro;
import servicos.LivroService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ListarLivrosPanel extends JPanel {

    private JTable tabela = new JTable();
    private DefaultTableModel modelo = new DefaultTableModel();
    private JButton btnVoltar = new JButton("Voltar");
    private JButton btnExcluir = new JButton("Excluir");

    private Runnable onVoltar;
    private LivroService service;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ListarLivrosPanel(LivroService service) {
        this.service = service;

        setLayout(new BorderLayout());

        modelo.setColumnIdentifiers(
            new String[]{"ID", "Título", "Autor", "Gênero", "Data"}
        );

        tabela.setModel(modelo);
        tabela.setDefaultEditor(Object.class, null);

        JScrollPane scroll = new JScrollPane(tabela);

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnVoltar);
        painelBotoes.add(btnExcluir);

        add(scroll, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        btnVoltar.addActionListener(e -> {
            if (onVoltar != null) onVoltar.run();
        });

        btnExcluir.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) return;

            int id = (int) modelo.getValueAt(linhaSelecionada, 0);
            try {
                service.excluir(id);
                atualizarTabela();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        atualizarTabela();
    }

    public void setListener(Runnable r) {
        onVoltar = r;
    }

    public void atualizarTabela() {
        try {
            List<Livro> livros = service.listar();
            modelo.setRowCount(0);

            for (Livro l : livros) {
                modelo.addRow(new Object[]{
                    l.getId(),
                    l.getTitulo(),
                    l.getAutor(),
                    l.getGenero(),
                    l.getDataPublicacao().format(formatter)
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
