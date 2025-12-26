package servicos;

import modelo.Livro;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class LivroServiceTest {

    @Test
    public void testValidarLivroDuplicado() {
        byte[] pdf = new byte[]{1, 2};
        LocalDate data = LocalDate.now();

        Livro livro = new Livro("Java Avançado", "Maria Souza", "Tecnologia", data, pdf);

        LivroService service = new LivroService() {
            @Override
            public void inserir(Livro livro) throws Exception {
                throw new Exception("Livro já existe");
            }
        };

        try {
            service.inserir(livro);
            fail("Era esperado lançar exceção");
        } catch (Exception e) {
            assertEquals("Livro já existe", e.getMessage());
        }
    }
}
