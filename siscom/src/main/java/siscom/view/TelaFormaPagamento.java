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

import siscom.controller.FormaPagamentoController;
import siscom.model.FormaPagamento;

public class TelaFormaPagamento extends JFrame {

    private JComboBox<Integer> cbId;
    private JTextField txtNome;
    private JComboBox<String> cbTipo;
    private JTextField txtPrazo;
    private JTextField txtParcelas;

    private JButton btnSalvar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnPesquisar;
    private JButton btnImprimir;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private FormaPagamentoController controller;

    public TelaFormaPagamento() {

        controller = new FormaPagamentoController();

        setTitle("Cadastro Forma de Pagamento");
        setSize(820,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        JPanel painelCampos = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx=0;
        gbc.gridy=0;
        painelCampos.add(new JLabel("ID:"),gbc);

        gbc.gridx=1;
        cbId = new JComboBox<>();
        cbId.addItem(null);
        cbId.setPrototypeDisplayValue(999999);
        painelCampos.add(cbId,gbc);

        gbc.gridx=0;
        gbc.gridy++;
        painelCampos.add(new JLabel("Nome:"),gbc);

        gbc.gridx=1;
        txtNome = new JTextField(25);
        painelCampos.add(txtNome,gbc);

        gbc.gridx=0;
        gbc.gridy++;
        painelCampos.add(new JLabel("Tipo:"),gbc);

        gbc.gridx=1;
        cbTipo = new JComboBox<>();
        cbTipo.addItem("À Vista");
        cbTipo.addItem("A Prazo");
        painelCampos.add(cbTipo,gbc);

        gbc.gridx=0;
        gbc.gridy++;
        painelCampos.add(new JLabel("Prazo (dias):"),gbc);

        gbc.gridx=1;
        txtPrazo = new JTextField(10);
        painelCampos.add(txtPrazo,gbc);

        gbc.gridx=0;
        gbc.gridy++;
        painelCampos.add(new JLabel("Qtd Parcelas:"),gbc);

        gbc.gridx=1;
        txtParcelas = new JTextField(10);
        painelCampos.add(txtParcelas,gbc);

        JPanel painelBotoes = new JPanel(new FlowLayout());

        btnSalvar = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnPesquisar = new JButton("Pesquisar");
        btnImprimir = new JButton("Imprimir");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnPesquisar);
        painelBotoes.add(btnImprimir);

        modeloTabela = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Nome",
                        "Tipo",
                        "Prazo",
                        "Parcelas"
                },0){

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

                    int linha=tabela.getSelectedRow();

                    cbId.setSelectedItem(
                            Integer.parseInt(
                                    modeloTabela.getValueAt(linha,0).toString()));

                    txtNome.setText(
                            modeloTabela.getValueAt(linha,1).toString());

                    cbTipo.setSelectedItem(
                            modeloTabela.getValueAt(linha,2).toString());

                    txtPrazo.setText(
                            modeloTabela.getValueAt(linha,3).toString());

                    txtParcelas.setText(
                            modeloTabela.getValueAt(linha,4).toString());

                }

            }

        });

        cbTipo.addActionListener(e->{

            boolean prazo = cbTipo.getSelectedIndex()==1;

            txtPrazo.setEnabled(prazo);
            txtParcelas.setEnabled(prazo);

            if(!prazo){

                txtPrazo.setText("0");
                txtParcelas.setText("1");

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
        btnImprimir.addActionListener(e->imprimir());

        cbTipo.setSelectedIndex(0);

        carregarTabela();

    }
        private void salvar() {

        FormaPagamento fp = new FormaPagamento();

        fp.setNome(txtNome.getText());
        fp.setAvistaPrazo(cbTipo.getSelectedIndex());

        fp.setPrazo(Integer.parseInt(txtPrazo.getText()));
        fp.setQtdeParcela(Integer.parseInt(txtParcelas.getText()));

        boolean sucesso = controller.salvar(fp);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Forma de pagamento salva com sucesso!");
            limpar();
            carregarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar.");
        }

    }

    private void alterar() {

        if (cbId.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um registro.");
            return;
        }

        FormaPagamento fp = new FormaPagamento();

        fp.setId((Integer) cbId.getSelectedItem());
        fp.setNome(txtNome.getText());
        fp.setAvistaPrazo(cbTipo.getSelectedIndex());

        fp.setPrazo(Integer.parseInt(txtPrazo.getText()));
        fp.setQtdeParcela(Integer.parseInt(txtParcelas.getText()));

        boolean sucesso = controller.alterar(fp);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Alterado com sucesso!");
            carregarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao alterar.");
        }

    }

    private void excluir() {

        if (cbId.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um registro.");
            return;
        }

        int op = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION);

        if (op != JOptionPane.YES_OPTION)
            return;

        boolean sucesso = controller.excluir((Integer) cbId.getSelectedItem());

        if (sucesso) {

            limpar();
            carregarTabela();

            JOptionPane.showMessageDialog(this,
                    "Registro excluído com sucesso!");

        } else {

            JOptionPane.showMessageDialog(this,
                    "Erro ao excluir.");

        }

    }

    private void pesquisar() {

        if (cbId.getSelectedItem() == null) {

            carregarTabela();
            return;

        }

        FormaPagamento fp =
                controller.pesquisar((Integer) cbId.getSelectedItem());

        if (fp != null) {

            txtNome.setText(fp.getNome());

            cbTipo.setSelectedIndex(fp.getAvistaPrazo());

            txtPrazo.setText(String.valueOf(fp.getPrazo()));

            txtParcelas.setText(String.valueOf(fp.getQtdeParcela()));

        } else {

            JOptionPane.showMessageDialog(this,
                    "Registro não encontrado.");

        }

    }

    private void imprimir() {

        JOptionPane.showMessageDialog(this,
                "Implementar relatório no JasperReports.");

    }

    private void carregarTabela() {

        modeloTabela.setRowCount(0);

        cbId.removeAllItems();
        cbId.addItem(null);

        List<FormaPagamento> lista =
                controller.pesquisarTodos();

        for (FormaPagamento fp : lista) {

            String tipo =
                    fp.getAvistaPrazo() == 0 ?
                            "À Vista" :
                            "A Prazo";

            modeloTabela.addRow(new Object[] {

                    fp.getId(),
                    fp.getNome(),
                    tipo,
                    fp.getPrazo(),
                    fp.getQtdeParcela()

            });

            cbId.addItem(fp.getId());

        }

    }

    private void limpar() {

        cbId.setSelectedItem(null);

        txtNome.setText("");

        cbTipo.setSelectedIndex(0);

        txtPrazo.setText("0");

        txtParcelas.setText("1");

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new TelaFormaPagamento().setVisible(true));

    }

}