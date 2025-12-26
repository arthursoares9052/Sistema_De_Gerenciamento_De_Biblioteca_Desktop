package modelo;

import org.junit.Test;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class LivroTest {

    @Test
    public void testCriarLivroValido() {
        byte[] pdf = new byte[]{1, 2, 3};
        LocalDate data = LocalDate.of(2023, 12, 23);

        Livro livro = new Livro("Java Básico", "João Silva", "Tecnologia", data, pdf);

        assertEquals("Java Básico", livro.getTitulo());
        assertEquals("João Silva", livro.getAutor());
        assertEquals("Tecnologia", livro.getGenero());
        assertEquals(data, livro.getDataPublicacao());
        assertArrayEquals(pdf, livro.getPdf());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTituloNaoVazio() {
        byte[] pdf = new byte[]{1};
        LocalDate data = LocalDate.now();

        new Livro("", "Autor", "Gênero", data, pdf);
    }
}
