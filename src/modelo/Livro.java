package modelo;

import java.time.LocalDate;

public class Livro {

    private int id;
    private String titulo;
    private String autor;
    private String genero;
    private LocalDate dataPublicacao;
    private byte[] pdf;

    public Livro(String titulo, String autor, String genero, LocalDate dataPublicacao, byte[] pdf) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.dataPublicacao = dataPublicacao;
        this.pdf = pdf;
    }

    public Livro(int id, String titulo, String autor, String genero, LocalDate dataPublicacao, byte[] pdf) {
        this(titulo, autor, genero, dataPublicacao, pdf);
        this.id = id;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getGenero() { return genero; }
    public LocalDate getDataPublicacao() { return dataPublicacao; }
    public byte[] getPdf() { return pdf; }
}
