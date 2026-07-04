package siscom.view;

import siscom.controller.*;
import siscom.model.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class TelaFinanceiro extends JFrame {

    private JTextField txtId;
    private JTextField txtData;
    private JComboBox<String> cbPagarReceber;
    private JComboBox<TipoConta> cbTipoConta;
    private JComboBox<FormaPagamento> cbFormaPagamento;
    private JComboBox<Fornecedor> cbFornecedor;
    private JComboBox<Cliente> cbCliente;

    private FinanceiroController financeiroController =
            new FinanceiroController();

    private TipoContaController tipoContaController =
            new TipoContaController();

    private FormaPagamentoController formaPagamentoController =
            new FormaPagamentoController();

    private FornecedorController fornecedorController =
            new FornecedorController();

    private ClienteController clienteController =
            new ClienteController();

    public TelaFinanceiro() {
        setTitle("Tela Financeiro");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(8, 2));

        txtId = new JTextField();
        txtData = new JTextField(LocalDate.now().toString());

        cbPagarReceber = new JComboBox<>(
                new String[]{"Pagar", "Receber"}
        );

        cbTipoConta = new JComboBox<>();
        cbFormaPagamento = new JComboBox<>();
        cbFornecedor = new JComboBox<>();
        cbCliente = new JComboBox<>();

        carregarCombos();

        JButton btnSalvar = new JButton("Salvar");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnPesquisar = new JButton("Pesquisar");
        JButton btnRelatorio = new JButton("Relatório");

        add(new JLabel("ID"));
        add(txtId);

        add(new JLabel("Data"));
        add(txtData);

        add(new JLabel("Pagar / Receber"));
        add(cbPagarReceber);

        add(new JLabel("Tipo Conta"));
        add(cbTipoConta);

        add(new JLabel("Forma Pagamento"));
        add(cbFormaPagamento);

        add(new JLabel("Fornecedor"));
        add(cbFornecedor);

        add(new JLabel("Cliente"));
        add(cbCliente);

        JPanel botoes = new JPanel();

        botoes.add(btnSalvar);
        botoes.add(btnAlterar);
        botoes.add(btnExcluir);
        botoes.add(btnPesquisar);
        botoes.add(btnRelatorio);

        add(botoes);

        btnSalvar.addActionListener(e -> salvar());
        btnAlterar.addActionListener(e -> alterar());
        btnExcluir.addActionListener(e -> excluir());
        btnPesquisar.addActionListener(e -> pesquisar());

        btnRelatorio.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Relatório não implementado"));
    }

    private void carregarCombos() {
        cbTipoConta.removeAllItems();
        cbFormaPagamento.removeAllItems();
        cbFornecedor.removeAllItems();
        cbCliente.removeAllItems();

        for (TipoConta t : tipoContaController.pesquisarTodos()) {
            cbTipoConta.addItem(t);
        }

        for (FormaPagamento fp :
                formaPagamentoController.pesquisarTodos()) {
            cbFormaPagamento.addItem(fp);
        }

        for (Fornecedor f :
                fornecedorController.pesquisarTodos()) {
            cbFornecedor.addItem(f);
        }

        for (Cliente c :
                clienteController.pesquisarTodos()) {
            cbCliente.addItem(c);
        }
    }

    private Financeiro montarObjeto() {
        Financeiro financeiro = new Financeiro();

        if (!txtId.getText().isBlank()) {
            financeiro.setId(Integer.parseInt(txtId.getText()));
        }

        financeiro.setDataConta(LocalDate.parse(txtData.getText()));

        int tipo = cbPagarReceber.getSelectedIndex();
        financeiro.setPagarOuReceber(tipo);

        financeiro.setTipoConta(
                (TipoConta) cbTipoConta.getSelectedItem());

        financeiro.setFormaPagamento(
                (FormaPagamento) cbFormaPagamento.getSelectedItem());

        if (tipo == 0) {
            financeiro.setFornecedor(
                    (Fornecedor) cbFornecedor.getSelectedItem());
            financeiro.setCliente(null);
        } else {
            financeiro.setCliente(
                    (Cliente) cbCliente.getSelectedItem());
            financeiro.setFornecedor(null);
        }

        return financeiro;
    }

    private void salvar() {
        boolean ok = financeiroController.salvar(montarObjeto());

        JOptionPane.showMessageDialog(
                this,
                ok ? "Salvo com sucesso" : "Erro ao salvar"
        );
    }

    private void alterar() {
        boolean ok = financeiroController.alterar(montarObjeto());

        JOptionPane.showMessageDialog(
                this,
                ok ? "Alterado com sucesso" : "Erro ao alterar"
        );
    }

    private void excluir() {
        boolean ok = financeiroController.excluir(
                Integer.parseInt(txtId.getText())
        );

        JOptionPane.showMessageDialog(
                this,
                ok ? "Excluído com sucesso" : "Erro ao excluir"
        );
    }

    private void pesquisar() {
        Financeiro financeiro = financeiroController.pesquisar(
                Integer.parseInt(txtId.getText())
        );

        if (financeiro != null) {
            txtData.setText(financeiro.getDataConta().toString());

            cbPagarReceber.setSelectedIndex(
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
    }
}