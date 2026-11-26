package model;
import java.io.Serializable;
import java.math.BigDecimal;

public class Participante implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int codigo;
    private String nome;
    private BigDecimal orcamento;

    public Participante(int codigo, String nome, BigDecimal orcamento) {
        if (codigo <= 0) throw new IllegalArgumentException("Código deve ser positivo.");
        setNome(nome);
        setOrcamento(orcamento);
        this.codigo = codigo;
    }
    public int getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public BigDecimal getOrcamento() { return orcamento; }
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Informe o nome.");
        this.nome = nome.trim();
    }
    public void setOrcamento(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Orçamento deve ser maior ou igual a zero.");
        this.orcamento = valor.setScale(2, java.math.RoundingMode.HALF_UP);
    }
}
