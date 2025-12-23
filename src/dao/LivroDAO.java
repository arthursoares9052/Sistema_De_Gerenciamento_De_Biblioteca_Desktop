package dao;

import persistencia.ConexaoBanco;
import modelo.Livro;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    private final ConexaoBanco conexao = new ConexaoBanco();

    private Connection getConnection() throws SQLException {
        return conexao.getConexao();
    }

    public void inserirLivro(Livro livro) throws SQLException {
        String sql = "INSERT INTO livros (titulo, autor, genero, data_publicacao, pdf) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getGenero());
            stmt.setDate(4, Date.valueOf(livro.getDataPublicacao()));
            stmt.setBytes(5, livro.getPdf());

            stmt.executeUpdate();
        }
    }

    public List<Livro> listarLivros() throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                livros.add(new Livro(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("genero"),
                    rs.getDate("data_publicacao").toLocalDate(),
                    rs.getBytes("pdf")
                ));
            }
        }
        return livros;
    }

    public boolean excluirLivro(int id) throws SQLException {
        String sql = "DELETE FROM livros WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean existeLivro(Livro livro) throws SQLException {
        String sql = "SELECT COUNT(*) FROM livros WHERE lower(titulo) = ? AND lower(autor) = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, livro.getTitulo().toLowerCase());
            stmt.setString(2, livro.getAutor().toLowerCase());

            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }
}
