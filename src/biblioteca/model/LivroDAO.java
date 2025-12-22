package biblioteca.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {
    public Connection getConnection() throws SQLException {
        return Conexao.getConnection();
    }

    // Método NOVO para excluir livro por ID
    public boolean excluirLivro(int id) throws SQLException {
        String sql = "DELETE FROM livros WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Métodos existentes (MANTIDOS IGUAIS)
    public void inserirLivro(Livro livro) throws SQLException {
        String sql = "INSERT INTO livros (titulo, autor, genero, data_publicacao, pdf) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getGenero());
            stmt.setString(4, livro.getDataPublicacao());
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
                    rs.getString("data_publicacao"),
                    rs.getBytes("pdf")
                ));
            }
        }
        return livros;
    }

    public boolean existeLivro(Livro livro) throws SQLException {
        List<Livro> livros = listarLivros();
        for (Livro l : livros) {
            if (l.getTitulo().equalsIgnoreCase(livro.getTitulo()) &&
                l.getAutor().equalsIgnoreCase(livro.getAutor())) {
                return true;
            }
        }
        return false;
    }
}