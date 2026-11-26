package model;
import java.io.Serializable;
import java.math.*;
import java.util.*;

public class Planejamento implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Map<Integer, Participante> participantes = new TreeMap<>();
    private final List<Despesa> despesas = new ArrayList<>();
    public Collection<Participante> getParticipantes() { return Collections.unmodifiableCollection(participantes.values()); }
    public List<Despesa> getDespesas() { return Collections.unmodifiableList(despesas); }
    public Participante buscarParticipante(int codigo) { return participantes.get(codigo); }
    public void adicionarParticipante(Participante p) {
        if (participantes.containsKey(p.getCodigo())) throw new IllegalArgumentException("Código já cadastrado.");
        participantes.put(p.getCodigo(), p);
    }
    public void atualizarParticipante(int codigo, String nome, BigDecimal orcamento) {
        Participante p = participantes.get(codigo);
        if (p == null) throw new IllegalArgumentException("Participante não encontrado.");
        p.setNome(nome); p.setOrcamento(orcamento);
    }
    public void adicionarDespesa(Despesa d) { validarVinculos(d); despesas.add(d); }
    public void atualizarDespesa(int indice, Despesa d) {
        if (indice < 0 || indice >= despesas.size()) throw new IllegalArgumentException("Selecione uma despesa.");
        validarVinculos(d); despesas.set(indice, d);
    }
    private void validarVinculos(Despesa d) {
        for (int codigo : d.getCodigosParticipantes()) if (!participantes.containsKey(codigo))
            throw new IllegalArgumentException("Participante inexistente: " + codigo);
    }
    public void excluirDespesa(int indice) {
        if (indice < 0 || indice >= despesas.size()) throw new IllegalArgumentException("Selecione uma despesa.");
        despesas.remove(indice);
    }
    public void excluirParticipante(int codigo) {
        for (Despesa d : despesas) if (d.getCodigosParticipantes().contains(codigo))
            throw new IllegalArgumentException("Este participante possui despesas vinculadas.");
        if (participantes.remove(codigo) == null) throw new IllegalArgumentException("Participante não encontrado.");
    }
    public BigDecimal totalIndividual(int codigo) {
        BigDecimal total = BigDecimal.ZERO;
        for (Despesa d : despesas) total = total.add(d.calcularCota(codigo));
        return total.setScale(2, RoundingMode.HALF_UP);
    }
    public BigDecimal custoTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (int c : participantes.keySet()) total = total.add(totalIndividual(c));
        return total.setScale(2, RoundingMode.HALF_UP);
    }
    public String situacao(int codigo) {
        Participante p = participantes.get(codigo);
        BigDecimal diferenca = totalIndividual(codigo).subtract(p.getOrcamento());
        if (diferenca.compareTo(BigDecimal.ZERO) > 0) return "Excedido em R$ " + diferenca.setScale(2);
        return "Dentro do orçamento";
    }
}
