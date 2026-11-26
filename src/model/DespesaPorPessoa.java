package model;
import java.math.*;
import java.util.*;

public class DespesaPorPessoa extends Despesa {
    private static final long serialVersionUID = 1L;
    public DespesaPorPessoa(String d, BigDecimal v, List<Integer> codigos) { super(d, v, codigos); }
    @Override public BigDecimal calcularCota(int codigo) {
        return getCodigosParticipantes().contains(codigo) ? getValor() : new BigDecimal("0.00");
    }
    @Override public String getTipo() { return "Por pessoa"; }
}
