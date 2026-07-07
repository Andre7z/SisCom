package siscom.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import siscom.controller.TipoContaController;
import siscom.model.TipoConta;

public class TelaTipoConta extends JFrame {

    private JComboBox<Integer> cbId;
    private JTextField txtDescricao;

    private JButton btnSalvar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnPesquisar;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private TipoContaController controller;

    public TelaTipoConta() {

        setTitle("Cadastro de Tipo de Conta");
        setSize(700, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        controller = new TipoContaController();

        JPanel painelCampos = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        painelCampos.add(new JLabel("ID:"), gbc);

        gbc.gridx = 1;
        cbId = new JComboBox<>();
        cbId.addItem(null);
        cbId.setPrototypeDisplayValue(999999);
        painelCampos.add(cbId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        painelCampos.add(new JLabel("Descrição:"), gbc);

        gbc.gridx = 1;
        txtDescricao = new JTextField(25);
        painelCampos.add(txtDescricao, gbc);

        JPanel painelBotoes = new JPanel(new FlowLayout());

        btnSalvar = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnPesquisar = new JButton("Pesquisar");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnPesquisar);

        modeloTabela = new DefaultTableModel(
                new Object[]{"ID","Descrição"},0){

            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };

        tabela = new JTable(modeloTabela);

        tabela.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if(e.getClickCount()==2){

                    int linha = tabela.getSelectedRow();

                    cbId.setSelectedItem(
                            Integer.parseInt(modeloTabela.getValueAt(linha,0).toString()));

                    txtDescricao.setText(
                            modeloTabela.getValueAt(linha,1).toString());
                }

            }

        });

        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.add(painelCampos,BorderLayout.NORTH);
        painelCentral.add(new JScrollPane(tabela),BorderLayout.CENTER);

        add(painelCentral,BorderLayout.CENTER);
        add(painelBotoes,BorderLayout.SOUTH);

        btnSalvar.addActionListener(e->salvar());
        btnAlterar.addActionListener(e->alterar());
        btnExcluir.addActionListener(e->excluir());
        btnPesquisar.addActionListener(e->pesquisar());

        carregarTabela();

    }

    private void salvar(){

        TipoConta tipo = new TipoConta();

        tipo.setDescricao(txtDescricao.getText());

        if(controller.salvar(tipo)){
            JOptionPane.showMessageDialog(this,"Salvo com sucesso!");
            limpar();
            carregarTabela();
        }else{
            JOptionPane.showMessageDialog(this,"Erro ao salvar.");
        }

    }

    private void alterar(){

        if(cbId.getSelectedItem()==null){
            JOptionPane.showMessageDialog(this,"Selecione um registro.");
            return;
        }

        TipoConta tipo = new TipoConta();

        tipo.setId((Integer)cbId.getSelectedItem());
        tipo.setDescricao(txtDescricao.getText());

        if(controller.alterar(tipo)){
            JOptionPane.showMessageDialog(this,"Alterado com sucesso!");
            carregarTabela();
        }else{
            JOptionPane.showMessageDialog(this,"Erro ao alterar.");
        }

    }

    private void excluir(){

        if(cbId.getSelectedItem()==null){
            JOptionPane.showMessageDialog(this,"Selecione um registro.");
            return;
        }

        int op = JOptionPane.showConfirmDialog(
                this,
                "Deseja excluir?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION);

        if(op!=JOptionPane.YES_OPTION)
            return;

        if(controller.excluir((Integer)cbId.getSelectedItem())){
            JOptionPane.showMessageDialog(this,"Excluído!");
            limpar();
            carregarTabela();
        }else{
            JOptionPane.showMessageDialog(this,"Erro ao excluir.");
        }

    }

    private void pesquisar(){

        if(cbId.getSelectedItem()==null){
            carregarTabela();
            return;
        }

        TipoConta tipo = controller.pesquisar((Integer)cbId.getSelectedItem());

        if(tipo!=null){
            txtDescricao.setText(tipo.getDescricao());
        }else{
            JOptionPane.showMessageDialog(this,"Registro não encontrado.");
        }

    }


    private void carregarTabela(){

        modeloTabela.setRowCount(0);
        cbId.removeAllItems();
        cbId.addItem(null);

        List<TipoConta> lista = controller.pesquisarTodos();

        for(TipoConta t : lista){

            modeloTabela.addRow(new Object[]{
                    t.getId(),
                    t.getDescricao()
            });

            cbId.addItem(t.getId());

        }

    }

    private void limpar(){

        cbId.setSelectedItem(null);
        txtDescricao.setText("");

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                ()->new TelaTipoConta().setVisible(true));

    }

}