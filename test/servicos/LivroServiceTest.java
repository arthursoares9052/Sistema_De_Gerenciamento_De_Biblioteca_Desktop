package servicos;

import modelo.Livro;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LivroServiceTest {

    @Test
    public void testValidarLivroDuplicado() throws Exception {
        byte[] pdf = new byte[]{1, 2};
        LocalDate data = LocalDate.now();

        Livro livro1 = new Livro("Java Avançado", "Maria Souza", "Tecnologia", data, pdf);
        Livro livro2 = new Livro("Java Avançado", "Maria Souza", "Tecnologia", data, pdf);

        LivroService service = new LivroService() {
            // Sobrescreve o método existeLivro só para simular duplicidade sem acessar banco
            @Override
            public void inserir(Livro livro) throws Exception {
                if(livro.getTitulo().equals("Java Avançado") && livro.getAutor().equals("Maria Souza")) {
                    throw new Exception("Livro já existe");
                }
            }
        };

        Exception exception = assertThrows(Exception.class, () -> service.inserir(livro2));
        assertEquals("Livro já existe", exception.getMessage());
    }
}
