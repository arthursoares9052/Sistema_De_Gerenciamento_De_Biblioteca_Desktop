package biblioteca.view;

import biblioteca.model.Livro;
import biblioteca.model.LivroDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InserirLivroPanel extends JPanel {
    private JTextField tituloField = new JTextField(30);
    private JTextField autorField = new JTextField(30);
    private JTextField generoField = new JTextField(15);
    private JTextField dataField = new JTextField(10);
    private JTextField caminhoPdfField = new JTextField(30);
    private JButton btnEscolherPdf = new JButton("Escolher PDF");
    private JButton btnInserir = new JButton("Inserir Livro");
    private JButton btnIrParaLista = new JButton("Ver Livros");

    private byte[] pdfBytes = null;
    private Runnable onSucesso;

    public InserirLivroPanel() {
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0; form.add(new JLabel("Título:"), c);
        c.gridx = 1; form.add(tituloField, c);

        c.gridx = 0; c.gridy = 1; form.add(new JLabel("Autor:"), c);
        c.gridx = 1; form.add(autorField, c);

        c.gridx = 0; c.gridy = 2; form.add(new JLabel("Gênero:"), c);
        c.gridx = 1; form.add(generoField, c);

        c.gridx = 0; c.gridy = 3; form.add(new JLabel("Data de publicação:"), c);
        c.gridx = 1; form.add(dataField, c);

        c.gridx = 0; c.gridy = 4; form.add(new JLabel("Arquivo PDF:"), c);
        c.gridx = 1; form.add(caminhoPdfField, c);
        c.gridx = 2; form.add(btnEscolherPdf, c);

        JPanel botoes = new JPanel();
        botoes.add(btnInserir);
        botoes.add(btnIrParaLista);

        add(form, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        caminhoPdfField.setEditable(false);

        btnEscolherPdf.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int res = chooser.showOpenDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                try {
                    pdfBytes = java.nio.file.Files.readAllBytes(chooser.getSelectedFile().toPath());
                    caminhoPdfField.setText(chooser.getSelectedFile().getAbsolutePath());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao carregar PDF: " + ex.getMessage());
                }
            }
        });

        btnInserir.addActionListener(e -> {
            try {
                String titulo = tituloField.getText().trim();
                String autor = autorField.getText().trim();
                String genero = generoField.getText().trim();
                String data = dataField.getText().trim();

                if(titulo.isEmpty() || autor.isEmpty() || genero.isEmpty() || data.isEmpty() || pdfBytes == null) {
                    JOptionPane.showMessageDialog(this, "Preencha todos os campos e escolha um PDF.");
                    return;
                }

                Livro livro = new Livro(titulo, autor, genero, data, pdfBytes);
                LivroDAO dao = new LivroDAO();

                if(!dao.existeLivro(livro)) {
                    dao.inserirLivro(livro);
                    JOptionPane.showMessageDialog(this, "Livro inserido com sucesso.");
                    limparCampos();
                    if(onSucesso != null) onSucesso.run();
                } else {
                    JOptionPane.showMessageDialog(this, "Livro já existe.");
                }
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        btnIrParaLista.addActionListener(e -> {
            if(onSucesso != null) onSucesso.run();
        });
    }

    public void setListener(Runnable r) {
        onSucesso = r;
    }

    private void limparCampos() {
        tituloField.setText("");
        autorField.setText("");
        generoField.setText("");
        dataField.setText("");
        caminhoPdfField.setText("");
        pdfBytes = null;
    }
}