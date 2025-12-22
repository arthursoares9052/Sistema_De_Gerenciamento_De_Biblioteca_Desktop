package biblioteca.model;

public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private String genero;
    private String dataPublicacao;
    private byte[] pdf;

    // Construtores e getters (IGUALZINHO ao seu código)
    public Livro(String titulo, String autor, String genero, String dataPublicacao, byte[] pdf) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.dataPublicacao = dataPublicacao;
        this.pdf = pdf;
    }

    public Livro(int id, String titulo, String autor, String genero, String dataPublicacao, byte[] pdf) {
        this(titulo, autor, genero, dataPublicacao, pdf);
        this.id = id;
    }

    // Getters (MANTIDOS)
    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getGenero() { return genero; }
    public String getDataPublicacao() { return dataPublicacao; }
    public byte[] getPdf() { return pdf; }
}