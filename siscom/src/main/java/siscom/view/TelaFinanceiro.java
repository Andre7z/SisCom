package siscom.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
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

import siscom.controller.ClienteController;
import siscom.controller.FinanceiroController;
import siscom.controller.FormaPagamentoController;
import siscom.controller.FornecedorController;
import siscom.controller.TipoContaController;

import siscom.model.Cliente;
import siscom.model.Financeiro;
import siscom.model.FormaPagamento;
import siscom.model.Fornecedor;
import siscom.model.TipoConta;

public class TelaFinanceiro extends JFrame {

    private JComboBox<Integer> cbId;

    private JTextField txtData;

    private JComboBox<String> cbTipoMovimento;

    private JComboBox<TipoConta> cbTipoConta;

    private JComboBox<FormaPagamento> cbFormaPagamento;

    private JComboBox<Fornecedor> cbFornecedor;

    private JComboBox<Cliente> cbCliente;

    private JButton btnSalvar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnPesquisar;
    private JButton btnImprimir;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private FinanceiroController financeiroController;
    private ClienteController clienteController;
    private FornecedorController fornecedorController;
    private TipoContaController tipoContaController;
    private FormaPagamentoController formaPagamentoController;
        public TelaFinanceiro(){

        financeiroController = new FinanceiroController();
        clienteController = new ClienteController();
        fornecedorController = new FornecedorController();
        tipoContaController = new TipoContaController();
        formaPagamentoController = new FormaPagamentoController();

        setTitle("Cadastro Financeiro");
        setSize(950,600);
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
        painelCampos.add(cbId);

        gbc.gridx=0;
        gbc.gridy++;

        painelCampos.add(new JLabel("Data:"),gbc);

        gbc.gridx=1;

        txtData = new JTextField(15);
        txtData.setText(LocalDate.now().toString());

        painelCampos.add(txtData);

        gbc.gridx=0;
        gbc.gridy++;

        painelCampos.add(new JLabel("Movimento:"),gbc);

        gbc.gridx=1;

        cbTipoMovimento = new JComboBox<>();

        cbTipoMovimento.addItem("Pagar");
        cbTipoMovimento.addItem("Receber");

        painelCampos.add(cbTipoMovimento);
                gbc.gridx = 0;
        gbc.gridy++;
        painelCampos.add(new JLabel("Tipo Conta:"), gbc);

        gbc.gridx = 1;
        cbTipoConta = new JComboBox<>();
        painelCampos.add(cbTipoConta);

        gbc.gridx = 0;
        gbc.gridy++;
        painelCampos.add(new JLabel("Forma Pagamento:"), gbc);

        gbc.gridx = 1;
        cbFormaPagamento = new JComboBox<>();
        painelCampos.add(cbFormaPagamento);

        gbc.gridx = 0;
        gbc.gridy++;
        painelCampos.add(new JLabel("Fornecedor:"), gbc);

        gbc.gridx = 1;
        cbFornecedor = new JComboBox<>();
        painelCampos.add(cbFornecedor);

        gbc.gridx = 0;
        gbc.gridy++;
        painelCampos.add(new JLabel("Cliente:"), gbc);

        gbc.gridx = 1;
        cbCliente = new JComboBox<>();
        painelCampos.add(cbCliente);

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
                        "Data",
                        "Tipo",
                        "Conta",
                        "Pagamento"
                },0){

            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }

        };

        tabela = new JTable(modeloTabela);

        tabela.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e){

                if(e.getClickCount()==2){

                    pesquisar();

                }

            }

        });

        JPanel painelCentral = new JPanel(new BorderLayout());

        painelCentral.add(painelCampos,BorderLayout.NORTH);
        painelCentral.add(new JScrollPane(tabela),BorderLayout.CENTER);

        add(painelCentral,BorderLayout.CENTER);
        add(painelBotoes,BorderLayout.SOUTH);

        carregarCombos();

        carregarTabela();

        cbTipoMovimento.addActionListener(e -> {

            boolean pagar = cbTipoMovimento.getSelectedIndex()==0;

            cbFornecedor.setEnabled(pagar);

            cbCliente.setEnabled(!pagar);

            if(pagar){
                cbCliente.setSelectedItem(null);
            }else{
                cbFornecedor.setSelectedItem(null);
            }

        });

        btnSalvar.addActionListener(e -> salvar());

        btnAlterar.addActionListener(e -> alterar());

        btnExcluir.addActionListener(e -> excluir());

        btnPesquisar.addActionListener(e -> pesquisar());

        btnImprimir.addActionListener(e -> imprimir());

        cbTipoMovimento.setSelectedIndex(0);

    }
    private void carregarCombos() {

    cbId.removeAllItems();
    cbId.addItem(null);

    cbTipoConta.removeAllItems();
    cbFormaPagamento.removeAllItems();
    cbFornecedor.removeAllItems();
    cbCliente.removeAllItems();

    // Financeiro
    List<Financeiro> financeiros = financeiroController.pesquisarTodos();

    for (Financeiro financeiro : financeiros) {
        cbId.addItem(financeiro.getId());
    }

    // Tipo Conta
    List<TipoConta> tipos = tipoContaController.pesquisarTodos();

    for (TipoConta tipo : tipos) {
        cbTipoConta.addItem(tipo);
    }

    // Forma Pagamento
    List<FormaPagamento> formas =
            formaPagamentoController.pesquisarTodos();

    for (FormaPagamento forma : formas) {
        cbFormaPagamento.addItem(forma);
    }

    // Fornecedor
    List<Fornecedor> fornecedores =
            fornecedorController.pesquisarTodos();

    for (Fornecedor fornecedor : fornecedores) {
        cbFornecedor.addItem(fornecedor);
    }

    // Cliente
    List<Cliente> clientes =
            clienteController.pesquisarTodos();

    for (Cliente cliente : clientes) {
        cbCliente.addItem(cliente);
    }

}
private void carregarTabela() {

    modeloTabela.setRowCount(0);

    List<Financeiro> lista =
            financeiroController.pesquisarTodos();

    for (Financeiro financeiro : lista) {

        String tipo =
                financeiro.getPagarOuReceber() == 0
                        ? "Pagar"
                        : "Receber";

        modeloTabela.addRow(new Object[]{

                financeiro.getId(),

                financeiro.getDataConta(),

                tipo,

                financeiro.getTipoConta().getDescricao(),

                financeiro.getFormaPagamento().getNome()

        });

    }

}
private void salvar() {

    try {

        Financeiro financeiro = new Financeiro();

        financeiro.setDataConta(LocalDate.parse(txtData.getText()));

        financeiro.setPagarOuReceber(
                cbTipoMovimento.getSelectedIndex());

        financeiro.setTipoConta(
                (TipoConta) cbTipoConta.getSelectedItem());

        financeiro.setFormaPagamento(
                (FormaPagamento) cbFormaPagamento.getSelectedItem());

        if (cbTipoMovimento.getSelectedIndex() == 0) {

            financeiro.setFornecedor(
                    (Fornecedor) cbFornecedor.getSelectedItem());

            financeiro.setCliente(null);

        } else {

            financeiro.setCliente(
                    (Cliente) cbCliente.getSelectedItem());

            financeiro.setFornecedor(null);

        }

        boolean sucesso = financeiroController.salvar(financeiro);

        if (sucesso) {

            JOptionPane.showMessageDialog(this,
                    "Financeiro salvo com sucesso!");

            limpar();

            carregarCombos();

            carregarTabela();

        } else {

            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar.");

        }

    } catch (Exception ex) {

        JOptionPane.showMessageDialog(this,
                "Dados inválidos.");

    }

}

private void alterar() {

    if (cbId.getSelectedItem() == null) {

        JOptionPane.showMessageDialog(this,
                "Selecione um registro.");

        return;
    }

    try {

        Financeiro financeiro = new Financeiro();

        financeiro.setId((Integer) cbId.getSelectedItem());

        financeiro.setDataConta(LocalDate.parse(txtData.getText()));

        financeiro.setPagarOuReceber(
                cbTipoMovimento.getSelectedIndex());

        financeiro.setTipoConta(
                (TipoConta) cbTipoConta.getSelectedItem());

        financeiro.setFormaPagamento(
                (FormaPagamento) cbFormaPagamento.getSelectedItem());

        if (cbTipoMovimento.getSelectedIndex() == 0) {

            financeiro.setFornecedor(
                    (Fornecedor) cbFornecedor.getSelectedItem());

            financeiro.setCliente(null);

        } else {

            financeiro.setCliente(
                    (Cliente) cbCliente.getSelectedItem());

            financeiro.setFornecedor(null);

        }

        boolean sucesso =
                financeiroController.alterar(financeiro);

        if (sucesso) {

            JOptionPane.showMessageDialog(this,
                    "Alterado com sucesso!");

            carregarTabela();

        } else {

            JOptionPane.showMessageDialog(this,
                    "Erro ao alterar.");

        }

    } catch (Exception ex) {

        JOptionPane.showMessageDialog(this,
                "Dados inválidos.");

    }

}

private void pesquisar() {

    if (cbId.getSelectedItem() == null) {

        carregarTabela();

        return;
    }

    Financeiro financeiro =
            financeiroController.pesquisar(
                    (Integer) cbId.getSelectedItem());

    if (financeiro == null) {

        JOptionPane.showMessageDialog(this,
                "Registro não encontrado.");

        return;
    }

    txtData.setText(financeiro.getDataConta().toString());

    cbTipoMovimento.setSelectedIndex(
            financeiro.getPagarOuReceber());

    cbTipoConta.setSelectedItem(
            financeiro.getTipoConta());

    cbFormaPagamento.setSelectedItem(
            financeiro.getFormaPagamento());

    cbFornecedor.setSelectedItem(
            financeiro.getFornecedor());

    cbCliente.setSelectedItem(
            financeiro.getCliente());

}

private void excluir() {

    if (cbId.getSelectedItem() == null) {

        JOptionPane.showMessageDialog(this,
                "Selecione um registro.");

        return;
    }

    int op = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente excluir?",
            "Confirmação",
            JOptionPane.YES_NO_OPTION);

    if (op != JOptionPane.YES_OPTION)
        return;

    boolean sucesso =
            financeiroController.excluir(
                    (Integer) cbId.getSelectedItem());

    if (sucesso) {

        JOptionPane.showMessageDialog(this,
                "Excluído com sucesso!");

        limpar();

        carregarCombos();

        carregarTabela();

    } else {

        JOptionPane.showMessageDialog(this,
                "Erro ao excluir.");

    }

}

private void imprimir() {

    JOptionPane.showMessageDialog(this,
            "Implementar JasperReports.");

}

private void limpar() {

    cbId.setSelectedItem(null);

    txtData.setText(LocalDate.now().toString());

    cbTipoMovimento.setSelectedIndex(0);

    if (cbTipoConta.getItemCount() > 0)
        cbTipoConta.setSelectedIndex(0);

    if (cbFormaPagamento.getItemCount() > 0)
        cbFormaPagamento.setSelectedIndex(0);

    cbFornecedor.setSelectedItem(null);

    cbCliente.setSelectedItem(null);

}

public static void main(String[] args) {

    SwingUtilities.invokeLater(() ->
            new TelaFinanceiro().setVisible(true));

}
}