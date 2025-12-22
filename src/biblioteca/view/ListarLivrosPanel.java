package biblioteca.view;

import biblioteca.model.Livro;
import biblioteca.model.LivroDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListarLivrosPanel extends JPanel {
    private JTable tabela = new JTable();
    private DefaultTableModel modelo = new DefaultTableModel();
    private JButton btnVoltar = new JButton("Voltar para Inserir");
    private JButton btnExcluir = new JButton("Excluir Livro"); // Botão NOVO
    private Runnable onVoltar;

    public ListarLivrosPanel() {
        setLayout(new BorderLayout());

        modelo.setColumnIdentifiers(new String[]{"ID", "Título", "Autor", "Gênero", "Data Publicação"});
        tabela.setModel(modelo);
        tabela.setDefaultEditor(Object.class, null);

        JScrollPane scroll = new JScrollPane(tabela);

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnVoltar);
        painelBotoes.add(btnExcluir); // Adiciona o botão novo

        add(scroll, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        // Listener do botão "Voltar" (existente)
        btnVoltar.addActionListener(e -> {
            if (onVoltar != null) onVoltar.run();
        });

        // Listener NOVO do botão "Excluir"
        btnExcluir.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um livro para excluir!");
                return;
            }

            int id = (int) modelo.getValueAt(linhaSelecionada, 0); // Pega o ID da linha selecionada
            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir este livro?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
            );

            if (confirmacao == JOptionPane.YES_OPTION) {
                try {
                    LivroDAO dao = new LivroDAO();
                    if (dao.excluirLivro(id)) {
                        JOptionPane.showMessageDialog(this, "Livro excluído com sucesso!");
                        atualizarTabela(); // Atualiza a tabela após exclusão
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
                }
            }
        });

        atualizarTabela();
    }

    public void setListener(Runnable r) {
        onVoltar = r;
    }

    public void atualizarTabela() {
        try {
            LivroDAO dao = new LivroDAO();
            List<Livro> livros = dao.listarLivros();

            modelo.setRowCount(0);
            for (Livro l : livros) {
                modelo.addRow(new Object[]{
                    l.getId(),
                    l.getTitulo(),
                    l.getAutor(),
                    l.getGenero(),
                    l.getDataPublicacao()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar livros: " + ex.getMessage());
        }
    }
}