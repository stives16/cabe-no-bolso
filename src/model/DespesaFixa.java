package model;
import java.math.*;
import java.util.*;

public class DespesaFixa extends Despesa {
    private static final long serialVersionUID = 1L;
    public DespesaFixa(String d, BigDecimal v, List<Integer> codigos) { super(d, v, codigos); }
    @Override public BigDecimal calcularCota(int codigo) {
        List<Integer> ordenados = new ArrayList<>(getCodigosParticipantes());
        Collections.sort(ordenados);
        int pos = ordenados.indexOf(codigo);
        if (pos < 0) return new BigDecimal("0.00");
        long centavos = getValor().movePointRight(2).longValueExact();
        long base = centavos / ordenados.size();
        long resto = centavos % ordenados.size();
        return BigDecimal.valueOf(base + (pos < resto ? 1 : 0), 2);
    }
    @Override public String getTipo() { return "Fixa"; }
}
