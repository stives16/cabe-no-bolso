package controller;
import model.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.List;

public class PlanejamentoController {
    private Planejamento planejamento = new Planejamento();
    private final Persistencia persistencia = new ArquivoPersistencia("planejamento.dat");
    public Planejamento getPlanejamento() { return planejamento; }
    public void cadastrarParticipante(int codigo, String nome, BigDecimal orcamento) { planejamento.adicionarParticipante(new Participante(codigo, nome, orcamento)); }
    public void atualizarParticipante(int codigo, String nome, BigDecimal orcamento) { planejamento.atualizarParticipante(codigo, nome, orcamento); }
    private Despesa criarDespesa(String descricao, BigDecimal valor, String tipo, List<Integer> codigos) {
        return tipo.equals("Fixa") ? new DespesaFixa(descricao, valor, codigos) : new DespesaPorPessoa(descricao, valor, codigos);
    }
    public void cadastrarDespesa(String descricao, BigDecimal valor, String tipo, List<Integer> codigos) { planejamento.adicionarDespesa(criarDespesa(descricao, valor, tipo, codigos)); }
    public void atualizarDespesa(int indice, String descricao, BigDecimal valor, String tipo, List<Integer> codigos) { planejamento.atualizarDespesa(indice, criarDespesa(descricao, valor, tipo, codigos)); }
    public void excluirDespesa(int indice) { planejamento.excluirDespesa(indice); }
    public void excluirParticipante(int codigo) { planejamento.excluirParticipante(codigo); }
    public void salvar() throws IOException { persistencia.salvar(planejamento); }
    public void carregar() throws IOException, ClassNotFoundException { planejamento = persistencia.carregar(); }
}
