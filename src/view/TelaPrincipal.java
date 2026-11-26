package view;
import controller.PlanejamentoController;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

public class TelaPrincipal extends JFrame {
    private final PlanejamentoController controller;
    private final DefaultListModel<String> modeloP = new DefaultListModel<>(), modeloD = new DefaultListModel<>();
    private final JList<String> listaP = new JList<>(modeloP), listaD = new JList<>(modeloD);
    private final JTextArea resumo = new JTextArea();
    public TelaPrincipal(PlanejamentoController c) {
        super("Cabe no bolso - Planejador de despesas compartilhadas"); controller=c;
        setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(900,620); setLocationRelativeTo(null);
        JTabbedPane abas=new JTabbedPane(); abas.addTab("Participantes", painelParticipantes()); abas.addTab("Despesas", painelDespesas());
        resumo.setEditable(false); resumo.setFont(new Font(Font.MONOSPACED,Font.PLAIN,14)); abas.addTab("Resumo",new JScrollPane(resumo));
        add(abas,BorderLayout.CENTER);
        JPanel rodape=new JPanel(); JButton atualizar=new JButton("Atualizar resumo"), salvar=new JButton("Salvar planejamento"), carregar=new JButton("Carregar planejamento");
        atualizar.addActionListener(e->atualizarTudo()); salvar.addActionListener(e->executar(()->controller.salvar(),"Planejamento salvo em planejamento.dat."));
        carregar.addActionListener(e->executar(()->{controller.carregar();atualizarTudo();},"Planejamento carregado."));
        rodape.add(atualizar);rodape.add(salvar);rodape.add(carregar);add(rodape,BorderLayout.SOUTH); atualizarTudo();
    }
    private JPanel painelParticipantes(){
        JPanel p=new JPanel(new BorderLayout(10,10)); p.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        p.add(new JScrollPane(listaP),BorderLayout.CENTER); JPanel botoes=new JPanel();
        JButton add=new JButton("Cadastrar"), edit=new JButton("Editar selecionado"), del=new JButton("Excluir selecionado");
        add.addActionListener(e->dialogoParticipante(null)); edit.addActionListener(e->{int i=listaP.getSelectedIndex();if(i<0){aviso("Selecione um participante.");return;}dialogoParticipante(new ArrayList<>(controller.getPlanejamento().getParticipantes()).get(i));});
        del.addActionListener(e->{int i=listaP.getSelectedIndex();if(i<0){aviso("Selecione um participante.");return;}Participante x=new ArrayList<>(controller.getPlanejamento().getParticipantes()).get(i);executar(()->controller.excluirParticipante(x.getCodigo()),"Participante excluído.");atualizarTudo();});
        botoes.add(add);botoes.add(edit);botoes.add(del);p.add(botoes,BorderLayout.SOUTH);return p;
    }
    private void dialogoParticipante(Participante atual){
        JTextField codigo=new JTextField(atual==null?"":String.valueOf(atual.getCodigo())); JTextField nome=new JTextField(atual==null?"":atual.getNome()); JTextField orc=new JTextField(atual==null?"":atual.getOrcamento().toString());
        if(atual!=null)codigo.setEditable(false);
        Object[] campos={"Código:",codigo,"Nome:",nome,"Orçamento (ex.: 70,00):",orc};
        if(JOptionPane.showConfirmDialog(this,campos,atual==null?"Novo participante":"Editar participante",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
            executar(()->{BigDecimal valor=dinheiro(orc.getText());if(atual==null)controller.cadastrarParticipante(Integer.parseInt(codigo.getText().trim()),nome.getText(),valor);else controller.atualizarParticipante(atual.getCodigo(),nome.getText(),valor);},"Participante salvo.");atualizarTudo();}
    }
    private JPanel painelDespesas(){
        JPanel p=new JPanel(new BorderLayout(10,10));p.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));p.add(new JScrollPane(listaD),BorderLayout.CENTER);JPanel botoes=new JPanel();
        JButton add=new JButton("Cadastrar despesa"),edit=new JButton("Editar selecionada"),del=new JButton("Excluir selecionada");
        add.addActionListener(e->dialogoDespesa(-1,null));edit.addActionListener(e->{int i=listaD.getSelectedIndex();if(i<0){aviso("Selecione uma despesa.");return;}dialogoDespesa(i,controller.getPlanejamento().getDespesas().get(i));});
        del.addActionListener(e->{int i=listaD.getSelectedIndex();if(i<0){aviso("Selecione uma despesa.");return;}executar(()->controller.excluirDespesa(i),"Despesa excluída.");atualizarTudo();});
        botoes.add(add);botoes.add(edit);botoes.add(del);p.add(botoes,BorderLayout.SOUTH);return p;
    }
    private void dialogoDespesa(int indice,Despesa atual){
        JTextField descricao=new JTextField(atual==null?"":atual.getDescricao()); JTextField valor=new JTextField(atual==null?"":atual.getValor().toString());
        JComboBox<String> tipo=new JComboBox<>(new String[]{"Fixa","Por pessoa"});if(atual!=null)tipo.setSelectedItem(atual.getTipo());
        JTextField codigos=new JTextField(atual==null?"":juntar(atual.getCodigosParticipantes()));
        String participantes="Códigos cadastrados: ";for(Participante x:controller.getPlanejamento().getParticipantes())participantes+=x.getCodigo()+"="+x.getNome()+"  ";
        Object[] campos={"Descrição:",descricao,"Valor:",valor,"Tipo:",tipo,participantes,"Códigos envolvidos (separados por vírgula):",codigos};
        if(JOptionPane.showConfirmDialog(this,campos,atual==null?"Nova despesa":"Editar despesa",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
            executar(()->{List<Integer> ids=new ArrayList<>();for(String s:codigos.getText().split(",")){if(!s.isBlank())ids.add(Integer.parseInt(s.trim()));}if(indice<0)controller.cadastrarDespesa(descricao.getText(),dinheiro(valor.getText()),(String)tipo.getSelectedItem(),ids);else controller.atualizarDespesa(indice,descricao.getText(),dinheiro(valor.getText()),(String)tipo.getSelectedItem(),ids);},"Despesa salva.");atualizarTudo();}
    }
    private BigDecimal dinheiro(String s){try{String v=s.trim();if(v.contains(","))v=v.replace(".","").replace(",",".");return new BigDecimal(v);}catch(Exception e){throw new IllegalArgumentException("Informe um valor monetário válido, por exemplo 70,00.");}}
    private String juntar(List<Integer> ids){StringJoiner j=new StringJoiner(",");for(int id:ids)j.add(String.valueOf(id));return j.toString();}
    private void atualizarTudo(){modeloP.clear();for(Participante p:controller.getPlanejamento().getParticipantes())modeloP.addElement(p.getCodigo()+" - "+p.getNome()+" | Orçamento: R$ "+p.getOrcamento());modeloD.clear();int n=1;for(Despesa d:controller.getPlanejamento().getDespesas())modeloD.addElement(n+++" - "+d.getDescricao()+" | "+d.getTipo()+" | R$ "+d.getValor()+" | Códigos: "+d.getCodigosParticipantes());atualizarResumo();}
    private void atualizarResumo(){StringBuilder b=new StringBuilder("CABe NO BOLSO - RESUMO DO PLANEJAMENTO\n\nDESPESAS\n");for(Despesa d:controller.getPlanejamento().getDespesas())b.append("• ").append(d.getDescricao()).append(" | ").append(d.getTipo()).append(" | R$ ").append(d.getValor()).append(" | participantes: ").append(d.getCodigosParticipantes()).append('\n');b.append("\nTOTAL POR PARTICIPANTE\n");for(Participante p:controller.getPlanejamento().getParticipantes())b.append(p.getCodigo()).append(" - ").append(p.getNome()).append(" | Orçamento: R$ ").append(p.getOrcamento()).append(" | Total: R$ ").append(controller.getPlanejamento().totalIndividual(p.getCodigo())).append(" | ").append(controller.getPlanejamento().situacao(p.getCodigo())).append('\n');b.append("\nCUSTO TOTAL: R$ ").append(controller.getPlanejamento().custoTotal());resumo.setText(b.toString());}
    private void executar(Acao a,String sucesso){try{a.executar();JOptionPane.showMessageDialog(this,sucesso);}catch(Exception ex){aviso(ex.getMessage()==null?"Não foi possível concluir a operação.":ex.getMessage());}}
    private void aviso(String s){JOptionPane.showMessageDialog(this,s,"Atenção",JOptionPane.ERROR_MESSAGE);}
    @FunctionalInterface private interface Acao{void executar()throws Exception;}
}
