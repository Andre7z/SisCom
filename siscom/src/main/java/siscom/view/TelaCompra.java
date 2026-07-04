package siscom.view;

import siscom.controller.*;
import siscom.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TelaCompra extends JFrame {

    private JTextField txtId;
    private JTextField txtQuantidade;
    private JTextField txtValorUnitario;
    private JLabel lblTotal;

    private JComboBox<Fornecedor> cbFornecedor;
    private JComboBox<Produto> cbProduto;
    private JComboBox<FormaPagamento> cbFormaPagamento;
    private JComboBox<TipoConta> cbTipoConta;

    private JTable tabela;
    private DefaultTableModel model;

    private List<CompraProduto> itens = new ArrayList<>();

    private CompraController compraController = new CompraController();
    private FornecedorController fornecedorController = new FornecedorController();
    private ProdutoController produtoController = new ProdutoController();
    private FormaPagamentoController formaPagamentoController =
            new FormaPagamentoController();
    private TipoContaController tipoContaController =
            new TipoContaController();

    public TelaCompra() {
        setTitle("Tela Compra");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(8,2));

        txtId = new JTextField();
        txtQuantidade = new JTextField();
        txtValorUnitario = new JTextField();

        lblTotal = new JLabel("0.0");

        cbFornecedor = new JComboBox<>();
        cbProduto = new JComboBox<>();
        cbFormaPagamento = new JComboBox<>();
        cbTipoConta = new JComboBox<>();

        panel.add(new JLabel("Id"));
        panel.add(txtId);

        panel.add(new JLabel("Fornecedor"));
        panel.add(cbFornecedor);

        panel.add(new JLabel("Produto"));
        panel.add(cbProduto);

        panel.add(new JLabel("Quantidade"));
        panel.add(txtQuantidade);

        panel.add(new JLabel("Valor Unitário"));
        panel.add(txtValorUnitario);

        panel.add(new JLabel("Forma Pagamento"));
        panel.add(cbFormaPagamento);

        panel.add(new JLabel("Tipo Conta"));
        panel.add(cbTipoConta);

        panel.add(new JLabel("Valor Total"));
        panel.add(lblTotal);

        add(panel, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new Object[]{"Produto","Qtd","Valor","Subtotal"}, 0);

        tabela = new JTable(model);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel botoes = new JPanel();

        JButton btnAdicionar = new JButton("Adicionar Item");
        JButton btnSalvar = new JButton("Salvar");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnPesquisar = new JButton("Pesquisar");
        JButton btnRelatorio = new JButton("Relatório");

        botoes.add(btnAdicionar);
        botoes.add(btnSalvar);
        botoes.add(btnAlterar);
        botoes.add(btnExcluir);
        botoes.add(btnPesquisar);
        botoes.add(btnRelatorio);

        add(botoes, BorderLayout.SOUTH);

        carregarCombos();

        btnAdicionar.addActionListener(e -> adicionarItem());
        btnSalvar.addActionListener(e -> salvarCompra());
        btnAlterar.addActionListener(e -> alterarCompra());
        btnExcluir.addActionListener(e -> excluirCompra());
        btnPesquisar.addActionListener(e -> pesquisarCompra());
        btnRelatorio.addActionListener(e -> gerarRelatorio());
    }

    private void carregarCombos() {
        cbFornecedor.removeAllItems();
        cbProduto.removeAllItems();
        cbFormaPagamento.removeAllItems();
        cbTipoConta.removeAllItems();

        for (Fornecedor f : fornecedorController.pesquisarTodos())
            cbFornecedor.addItem(f);

        for (Produto p : produtoController.pesquisarTodos())
            cbProduto.addItem(p);

        for (FormaPagamento fp : formaPagamentoController.pesquisarTodos())
            cbFormaPagamento.addItem(fp);

        for (TipoConta tc : tipoContaController.pesquisarTodos())
            cbTipoConta.addItem(tc);
    }

    private void adicionarItem() {
        Produto produto = (Produto) cbProduto.getSelectedItem();

        int qtd = Integer.parseInt(txtQuantidade.getText());
        double valor = Double.parseDouble(txtValorUnitario.getText());

        CompraProduto item = new CompraProduto();
        item.setProduto(produto);
        item.setQuantidade(qtd);
        item.setValorUnitario(valor);

        itens.add(item);

        double subtotal = qtd * valor;

        model.addRow(new Object[]{
                produto,
                qtd,
                valor,
                subtotal
        });

        atualizarTotal();
    }

    private void atualizarTotal() {
        double total = 0;

        for (CompraProduto item : itens) {
            total += item.getQuantidade() * item.getValorUnitario();
        }

        lblTotal.setText(String.valueOf(total));
    }

    private void salvarCompra() {
        Compra compra = new Compra();

        compra.setFornecedor(
                (Fornecedor) cbFornecedor.getSelectedItem());

        compra.setProdutos(itens);
        compra.setDataCompra(LocalDate.now());

        boolean ok = compraController.salvar(
                compra,
                (FormaPagamento) cbFormaPagamento.getSelectedItem(),
                (TipoConta) cbTipoConta.getSelectedItem()
        );

        JOptionPane.showMessageDialog(this,
                ok ? "Compra salva" : "Erro");
    }

    private void alterarCompra() {
        Compra compra = compraController.pesquisar(
                Integer.parseInt(txtId.getText()));

        if (compra != null) {
            compra.setFornecedor(
                    (Fornecedor) cbFornecedor.getSelectedItem());
            compraController.alterar(compra);
            JOptionPane.showMessageDialog(this, "Alterado");
        }
    }

    private void excluirCompra() {
        compraController.excluir(
                Integer.parseInt(txtId.getText()));
        JOptionPane.showMessageDialog(this, "Excluído");
    }

    private void pesquisarCompra() {
        Compra compra = compraController.pesquisar(
                Integer.parseInt(txtId.getText()));

        if (compra != null) {
            cbFornecedor.setSelectedItem(compra.getFornecedor());
            lblTotal.setText(String.valueOf(compra.getValorTotal()));
        }
    }

    private void gerarRelatorio() {
        JOptionPane.showMessageDialog(this,
                "Relatório ainda não implementado");
    }
}