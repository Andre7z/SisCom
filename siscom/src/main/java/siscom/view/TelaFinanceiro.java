package siscom.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
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

    public TelaFinanceiro() {

        financeiroController = new FinanceiroController();
        clienteController = new ClienteController();
        fornecedorController = new FornecedorController();
        tipoContaController = new TipoContaController();
        formaPagamentoController = new FormaPagamentoController();

        setTitle("Cadastro Financeiro");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel painelCampos = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        painelCampos.add(new JLabel("ID:"), gbc);

        gbc.gridx = 1;
        cbId = new JComboBox<>();
        cbId.addItem(null);
        painelCampos.add(cbId, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        painelCampos.add(new JLabel("Data:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtData = new JTextField(15);
        txtData.setText(LocalDate.now().toString());
        painelCampos.add(txtData, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        painelCampos.add(new JLabel("Movimento:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cbTipoMovimento = new JComboBox<>();
        cbTipoMovimento.addItem("Pagar");
        cbTipoMovimento.addItem("Receber");
        painelCampos.add(cbTipoMovimento, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        painelCampos.add(new JLabel("Tipo Conta:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cbTipoConta = new JComboBox<>();
        painelCampos.add(cbTipoConta, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        painelCampos.add(new JLabel("Forma Pagamento:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cbFormaPagamento = new JComboBox<>();
        painelCampos.add(cbFormaPagamento, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        painelCampos.add(new JLabel("Fornecedor:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cbFornecedor = new JComboBox<>();
        painelCampos.add(cbFornecedor, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        painelCampos.add(new JLabel("Cliente:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cbCliente = new JComboBox<>();
        painelCampos.add(cbCliente, gbc);

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
                new Object[] {
                        "ID",
                        "Data",
                        "Tipo",
                        "Conta",
                        "Pagamento",
                        "Fornecedor/Cliente"
                }, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };

        tabela = new JTable(modeloTabela);

        tabela.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {

                    int linha = tabela.getSelectedRow();

                    if (linha == -1) {
                        return;
                    }

                    Integer id = (Integer) modeloTabela.getValueAt(linha, 0);

                    cbId.setSelectedItem(id);

                    pesquisar();

                }

            }

        });

        JPanel painelCentral = new JPanel(new BorderLayout());

        painelCentral.add(painelCampos, BorderLayout.NORTH);
        painelCentral.add(new JScrollPane(tabela), BorderLayout.CENTER);

        add(painelCentral, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        configurarRenderizadores();

        carregarCombos();

        carregarTabela();

        cbTipoMovimento.addActionListener(e -> atualizarCamposMovimento());

        btnSalvar.addActionListener(e -> salvar());

        btnAlterar.addActionListener(e -> alterar());

        btnExcluir.addActionListener(e -> excluir());

        btnPesquisar.addActionListener(e -> pesquisar());

        btnImprimir.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Relatório JasperReports"));

        cbTipoMovimento.setSelectedIndex(0);
        atualizarCamposMovimento();

    }

    private void atualizarCamposMovimento() {

        boolean pagar = cbTipoMovimento.getSelectedIndex() == 0;

        cbFornecedor.setEnabled(pagar);

        cbCliente.setEnabled(!pagar);

        if (pagar) {
            cbCliente.setSelectedItem(null);
        } else {
            cbFornecedor.setSelectedItem(null);
        }

    }

    private void configurarRenderizadores() {

        cbTipoConta.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                    Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index,
                        isSelected, cellHasFocus);

                if (value instanceof TipoConta tipo) {
                    setText(tipo.getDescricao());
                }

                return this;
            }
        });

        cbFormaPagamento.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                    Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index,
                        isSelected, cellHasFocus);

                if (value instanceof FormaPagamento forma) {
                    setText(forma.getNome());
                }

                return this;
            }
        });

        cbFornecedor.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                    Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index,
                        isSelected, cellHasFocus);

                if (value instanceof Fornecedor fornecedor) {
                    setText(fornecedor.getNomeFantasia());
                }

                return this;
            }
        });

        cbCliente.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                    Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index,
                        isSelected, cellHasFocus);

                if (value instanceof Cliente cliente) {
                    setText(cliente.getNome());
                }

                return this;
            }
        });
    }

    private void carregarCombos() {

        cbId.removeAllItems();
        cbId.addItem(null);

        cbTipoConta.removeAllItems();
        cbFormaPagamento.removeAllItems();
        cbFornecedor.removeAllItems();
        cbCliente.removeAllItems();

        List<Financeiro> financeiros = financeiroController.pesquisarTodos();

        for (Financeiro financeiro : financeiros) {
            cbId.addItem(financeiro.getId());
        }

        List<TipoConta> tipos = tipoContaController.pesquisarTodos();

        for (TipoConta tipo : tipos) {
            cbTipoConta.addItem(tipo);
        }

        List<FormaPagamento> formas = formaPagamentoController.pesquisarTodos();

        for (FormaPagamento forma : formas) {
            cbFormaPagamento.addItem(forma);
        }

        List<Fornecedor> fornecedores = fornecedorController.pesquisarTodos();

        for (Fornecedor fornecedor : fornecedores) {
            cbFornecedor.addItem(fornecedor);
        }

        List<Cliente> clientes = clienteController.pesquisarTodos();

        for (Cliente cliente : clientes) {
            cbCliente.addItem(cliente);
        }

    }

    private void carregarTabela() {

        modeloTabela.setRowCount(0);

        List<Financeiro> lista = financeiroController.pesquisarTodos();

        for (Financeiro financeiro : lista) {

            String tipo = financeiro.getPagarOuReceber() == 0
                    ? "Pagar"
                    : "Receber";

            String conta = financeiro.getTipoConta() != null
                    ? financeiro.getTipoConta().getDescricao()
                    : "-";

            String pagamento = financeiro.getFormaPagamento() != null
                    ? financeiro.getFormaPagamento().getNome()
                    : "-";

            String fornecedorCliente;

            if (financeiro.getFornecedor() != null) {
                fornecedorCliente = financeiro.getFornecedor().getNomeFantasia();
            } else if (financeiro.getCliente() != null) {
                fornecedorCliente = financeiro.getCliente().getNome();
            } else {
                fornecedorCliente = "-";
            }

            modeloTabela.addRow(new Object[] {

                    financeiro.getId(),

                    financeiro.getDataConta(),

                    tipo,

                    conta,

                    pagamento,

                    fornecedorCliente

            });

        }

    }

    private boolean validarCampos() {

        if (cbTipoConta.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione o tipo de conta.");
            return false;
        }

        if (cbFormaPagamento.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione a forma de pagamento.");
            return false;
        }

        boolean pagar = cbTipoMovimento.getSelectedIndex() == 0;

        if (pagar && cbFornecedor.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this,
                    "Movimento 'Pagar' exige um fornecedor selecionado.");
            return false;
        }

        if (!pagar && cbCliente.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this,
                    "Movimento 'Receber' exige um cliente selecionado.");
            return false;
        }

        return true;
    }

    private void salvar() {

        if (!validarCampos()) {
            return;
        }

        LocalDate dataConta;

        try {
            dataConta = LocalDate.parse(txtData.getText().trim());
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "Data inválida. Use o formato AAAA-MM-DD.");
            return;
        }

        try {

            Financeiro financeiro = new Financeiro();

            financeiro.setDataConta(dataConta);

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
                        "Financeiro salvo com sucesso! Id gerado: "
                                + financeiro.getId());

                limpar();

                carregarCombos();

                carregarTabela();

            } else {

                JOptionPane.showMessageDialog(this,
                        "Erro ao salvar. Verifique os dados informados.");

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

        if (!validarCampos()) {
            return;
        }

        LocalDate dataConta;

        try {
            dataConta = LocalDate.parse(txtData.getText().trim());
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "Data inválida. Use o formato AAAA-MM-DD.");
            return;
        }

        try {

            Financeiro financeiro = financeiroController.pesquisar(
                    (Integer) cbId.getSelectedItem());

            if (financeiro == null) {
                JOptionPane.showMessageDialog(this,
                        "Registro não encontrado.");
                return;
            }

            financeiro.setDataConta(dataConta);

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

            boolean sucesso = financeiroController.alterar(financeiro);

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

        Financeiro financeiro = financeiroController.pesquisar(
                (Integer) cbId.getSelectedItem());

        if (financeiro == null) {

            JOptionPane.showMessageDialog(this,
                    "Registro não encontrado.");

            return;
        }

        txtData.setText(financeiro.getDataConta().toString());

        cbTipoMovimento.setSelectedIndex(
                financeiro.getPagarOuReceber());

        atualizarCamposMovimento();

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

        boolean sucesso = financeiroController.excluir(
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

        SwingUtilities.invokeLater(() -> new TelaFinanceiro().setVisible(true));

    }
}