package servicos;

import dao.LivroDAO;
import modelo.Livro;
import java.util.List;

public class LivroService {

    private final LivroDAO dao = new LivroDAO();

    public void inserir(Livro livro) throws Exception {
        if (dao.existeLivro(livro)) {
            throw new Exception("Livro já existe");
        }
        dao.inserirLivro(livro);
    }

    public List<Livro> listar() throws Exception {
        return dao.listarLivros();
    }

    public void excluir(int id) throws Exception {
        if (!dao.excluirLivro(id)) {
            throw new Exception("Erro ao excluir");
        }
    }
}
