package model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

public abstract class Despesa implements Serializable {
    private static final long serialVersionUID = 1L;
    private String descricao;
    private BigDecimal valor;
    private final List<Integer> codigosParticipantes;

    protected Despesa(String descricao, BigDecimal valor, List<Integer> codigos) {
        setDescricao(descricao);
        setValor(valor);
        if (codigos == null || codigos.isEmpty()) throw new IllegalArgumentException("Selecione ao menos um participante.");
        if (new HashSet<>(codigos).size() != codigos.size()) throw new IllegalArgumentException("Participantes repetidos.");
        this.codigosParticipantes = new ArrayList<>(codigos);
    }
    public String getDescricao() { return descricao; }
    public BigDecimal getValor() { return valor; }
    public List<Integer> getCodigosParticipantes() { return Collections.unmodifiableList(codigosParticipantes); }
    public void setDescricao(String d) {
        if (d == null || d.isBlank()) throw new IllegalArgumentException("Informe a descrição.");
        descricao = d.trim();
    }
    public void setValor(BigDecimal v) {
        if (v == null || v.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Despesa deve ser maior que zero.");
        valor = v.setScale(2, java.math.RoundingMode.HALF_UP);
    }
    public abstract BigDecimal calcularCota(int codigo);
    public abstract String getTipo();
}
