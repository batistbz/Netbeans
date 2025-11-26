package model;

public class filme {

    private Integer id;               // Integer para permitir null quando não salvo ainda
    private String titulo;
    private String diretor;           // mapeado para coluna n_diretor no BD
    private Integer anoLancamento;    // Integer para permitir ausência
    private String genero;

    public filme() {}

    public filme(String titulo, String diretor, Integer anoLancamento, String genero) {
        this.titulo = titulo;
        this.diretor = diretor;
        this.anoLancamento = anoLancamento;
        this.genero = genero;
    }

    // getters e setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDiretor() { return diretor; }
    public void setDiretor(String diretor) { this.diretor = diretor; }

    public Integer getAnoLancamento() { return anoLancamento; }
    public void setAnoLancamento(Integer anoLancamento) { this.anoLancamento = anoLancamento; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    @Override
    public String toString() {
        return String.format("ID=%s | %s | %s | %s | %s",
                id == null ? "N/A" : id.toString(),
                titulo == null ? "-" : titulo,
                diretor == null ? "-" : diretor,
                anoLancamento == null ? "-" : anoLancamento.toString(),
                genero == null ? "-" : genero);
    }
}
