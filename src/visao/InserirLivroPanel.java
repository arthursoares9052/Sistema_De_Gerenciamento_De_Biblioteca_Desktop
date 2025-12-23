package visao;

import modelo.Livro;
import servicos.LivroService;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InserirLivroPanel extends JPanel {

    private JTextField tituloField = new JTextField(30);
    private JTextField autorField = new JTextField(30);
    private JTextField generoField = new JTextField(15);

    private JFormattedTextField dataField;
    private JTextField caminhoPdfField = new JTextField(30);

    private JButton btnEscolherPdf = new JButton("Escolher PDF");
    private JButton btnInserir = new JButton("Inserir Livro");
    private JButton btnIrParaLista = new JButton("Ver Livros");

    private byte[] pdfBytes;
    private Runnable onSucesso;

    private LivroService service;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public InserirLivroPanel(LivroService service) {
        this.service = service;

        setLayout(new BorderLayout());

        try {
            MaskFormatter mask = new MaskFormatter("##/##/####");
            mask.setPlaceholderCharacter('_');
            dataField = new JFormattedTextField(mask);
            dataField.setColumns(10);
        } catch (Exception e) {
            dataField = new JFormattedTextField();
        }

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

        c.gridx = 0; c.gridy = 3; form.add(new JLabel("Data:"), c);
        c.gridx = 1; form.add(dataField, c);

        c.gridx = 0; c.gridy = 4; form.add(new JLabel("PDF:"), c);
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
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    pdfBytes = Files.readAllBytes(chooser.getSelectedFile().toPath());
                    caminhoPdfField.setText(chooser.getSelectedFile().getAbsolutePath());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        });

        btnInserir.addActionListener(e -> {
            try {
                if (pdfBytes == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um PDF.");
                    return;
                }

                LocalDate data = LocalDate.parse(dataField.getText(), formatter);

                Livro livro = new Livro(
                    tituloField.getText().trim(),
                    autorField.getText().trim(),
                    generoField.getText().trim(),
                    data,
                    pdfBytes
                );

                service.inserir(livro);
                limpar();

                if (onSucesso != null) onSucesso.run();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Data inválida.");
            }
        });

        btnIrParaLista.addActionListener(e -> {
            if (onSucesso != null) onSucesso.run();
        });
    }

    public void setListener(Runnable r) {
        onSucesso = r;
    }

    private void limpar() {
        tituloField.setText("");
        autorField.setText("");
        generoField.setText("");
        dataField.setValue(null);
        caminhoPdfField.setText("");
        pdfBytes = null;
    }
}
