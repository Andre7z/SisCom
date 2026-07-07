package siscom.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.ArrayList;
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

import siscom.controller.VendaController;
import siscom.controller.FormaPagamentoController;
import siscom.controller.ClienteController;
import siscom.controller.ProdutoController;
import siscom.controller.TipoContaController;
import siscom.model.Venda;
import siscom.model.VendaProduto;
import siscom.model.FormaPagamento;
import siscom.model.Cliente;
import siscom.model.Produto;
import siscom.model.TipoConta;

public class TelaVenda extends JFrame {

    private JTextField txtId;
    private JTextField txtData;
    private JTextField txtQuantidade;
    private JTextField txtValorUnitario;
    private JTextField txtValorTotal;

    private JComboBox<Cliente> cbCliente;
    private JComboBox<Produto> cbProduto;
    private JComboBox<FormaPagamento> cbFormaPagamento;
    private JComboBox<TipoConta> cbTipoConta;

    private JButton btnAdicionar;
    private JButton btnRemover;

    private JButton btnSalvar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnPesquisar;
    private JButton btnImprimir;

    private JTable tabelaProdutos;
    private DefaultTableModel modeloTabela;

    private VendaController controller = new VendaController();
    private ProdutoController produtoController = new ProdutoController();
    private ClienteController clienteController = new ClienteController();
    private FormaPagamentoController formaPagamentoController = new FormaPagamentoController();
    private TipoContaController tipoContaController = new TipoContaController();

    private List<VendaProduto> listaProdutos = new ArrayList<>();

    public TelaVenda() {

        setTitle("Cadastro de Vendas");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel painelCampos = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        painelCampos.add(new JLabel("Id"), gbc);

        gbc.gridx = 1;
        txtId = new JTextField(10);
        painelCampos.add(txtId, gbc);

        gbc.gridx = 2;
        painelCampos.add(new JLabel("Data"), gbc);

        gbc.gridx = 3;
        txtData = new JTextField(10);
        txtData.setText(LocalDate.now().toString());
        painelCampos.add(txtData, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        painelCampos.add(new JLabel("Cliente"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;

        cbCliente = new JComboBox<>();
        cbCliente.setPreferredSize(new Dimension(350, 25));

        painelCampos.add(cbCliente, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 2;
        painelCampos.add(new JLabel("Forma Pagamento"), gbc);

        gbc.gridx = 1;

        cbFormaPagamento = new JComboBox<>();
        cbFormaPagamento.setPreferredSize(new Dimension(180, 25));
        painelCampos.add(cbFormaPagamento, gbc);

        gbc.gridx = 2;
        painelCampos.add(new JLabel("Tipo Conta"), gbc);

        gbc.gridx = 3;

        cbTipoConta = new JComboBox<>();
        cbTipoConta.setPreferredSize(new Dimension(180, 25));
        painelCampos.add(cbTipoConta, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        painelCampos.add(new JLabel("Produto"), gbc);

        gbc.gridx = 1;

        cbProduto = new JComboBox<>();
        cbProduto.setPreferredSize(new Dimension(250, 25));
        painelCampos.add(cbProduto, gbc);

        gbc.gridx = 2;
        painelCampos.add(new JLabel("Valor Unitário"), gbc);

        gbc.gridx = 3;

        txtValorUnitario = new JTextField(10);
        painelCampos.add(txtValorUnitario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        painelCampos.add(new JLabel("Quantidade"), gbc);

        gbc.gridx = 1;

        txtQuantidade = new JTextField(10);
        painelCampos.add(txtQuantidade, gbc);

        btnAdicionar = new JButton("Adicionar");
        btnRemover = new JButton("Remover");

        JPanel painelItem = new JPanel();

        painelItem.add(btnAdicionar);
        painelItem.add(btnRemover);

        gbc.gridx = 2;
        gbc.gridwidth = 2;

        painelCampos.add(painelItem, gbc);

        modeloTabela = new DefaultTableModel(
                new Object[] {
                        "Produto",
                        "Valor Unitário",
                        "Quantidade",
                        "Subtotal"
                }, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };

        tabelaProdutos = new JTable(modeloTabela);

        JScrollPane scroll = new JScrollPane(tabelaProdutos);

        JPanel painelSul = new JPanel(new BorderLayout());

        JPanel painelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        painelTotal.add(new JLabel("Valor Total"));

        txtValorTotal = new JTextField(12);
        txtValorTotal.setEditable(false);

        painelTotal.add(txtValorTotal);

        JPanel painelBotoes = new JPanel(new GridLayout(1, 5, 10, 10));

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

        painelSul.add(painelTotal, BorderLayout.NORTH);
        painelSul.add(painelBotoes, BorderLayout.SOUTH);

        add(painelCampos, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(painelSul, BorderLayout.SOUTH);

        carregarClientees();
        carregarProdutos();
        carregarFormaPagamento();
        carregarTipoConta();

        preencherValorUnitario();

        cbProduto.addActionListener(e -> preencherValorUnitario());

        btnAdicionar.addActionListener(e -> adicionarProduto());
        btnRemover.addActionListener(e -> removerProduto());

        btnSalvar.addActionListener(e -> acaoSalvar());
        btnAlterar.addActionListener(e -> acaoAlterar());
        btnExcluir.addActionListener(e -> acaoExcluir());
        btnPesquisar.addActionListener(e -> acaoPesquisar());

        // TipoConta = Receber
        for (int i = 0; i < cbTipoConta.getItemCount(); i++) {
            TipoConta tipo = cbTipoConta.getItemAt(i);

            if ("Receber".equalsIgnoreCase(tipo.getDescricao())) {
                cbTipoConta.setSelectedIndex(i);
                break;
            }
        }
        cbTipoConta.setEnabled(false);

    }

    private void carregarClientees() {

        cbCliente.removeAllItems();

        List<Cliente> lista = clienteController.pesquisarTodos();

        for (Cliente cliente : lista) {
            cbCliente.addItem(cliente);
        }

    }

    private void carregarProdutos() {

        cbProduto.removeAllItems();

        List<Produto> lista = produtoController.pesquisarTodos();

        for (Produto produto : lista) {
            cbProduto.addItem(produto);
        }

    }

    private void carregarFormaPagamento() {

        cbFormaPagamento.removeAllItems();

        List<FormaPagamento> lista = formaPagamentoController.pesquisarTodos();

        for (FormaPagamento forma : lista) {
            cbFormaPagamento.addItem(forma);
        }

    }

    private void carregarTipoConta() {

        cbTipoConta.removeAllItems();

        List<TipoConta> lista = tipoContaController.pesquisarTodos();

        for (TipoConta tipo : lista) {
            cbTipoConta.addItem(tipo);
        }

    }

    private void preencherValorUnitario() {

        Produto produto = (Produto) cbProduto.getSelectedItem();

        if (produto == null)
            return;

        if (produto.getValorUltimaVenda() != null &&
                produto.getValorUltimaVenda() > 0) {

            txtValorUnitario.setText(
                    produto.getValorUltimaVenda().toString());

        } else if (produto.getPreco() != null) {

            txtValorUnitario.setText(
                    produto.getPreco().toString());

        } else {

            txtValorUnitario.setText("0");

        }

    }

    private void adicionarProduto() {

        Produto produto = (Produto) cbProduto.getSelectedItem();

        if (produto == null) {

            JOptionPane.showMessageDialog(this,
                    "Selecione um produto.");

            return;

        }

        if (txtQuantidade.getText().isBlank()) {

            JOptionPane.showMessageDialog(this,
                    "Informe a quantidade.");

            return;

        }

        VendaProduto item = new VendaProduto();

        item.setProduto(produto);

        item.setQuantidade(
                Integer.parseInt(txtQuantidade.getText()));

        item.setValorUnitario(
                Double.parseDouble(txtValorUnitario.getText()));

        listaProdutos.add(item);

        modeloTabela.addRow(new Object[] {

                produto.getNome(),

                item.getValorUnitario(),

                item.getQuantidade(),

                item.getQuantidade() * item.getValorUnitario()

        });

        calcularValorTotal();

        txtQuantidade.setText("");

    }

    private void removerProduto() {

        int linha = tabelaProdutos.getSelectedRow();

        if (linha == -1)
            return;

        listaProdutos.remove(linha);

        modeloTabela.removeRow(linha);

        calcularValorTotal();

    }

    private void calcularValorTotal() {

        double total = 0;

        for (VendaProduto item : listaProdutos) {

            total += item.getQuantidade() *
                    item.getValorUnitario();

        }

        txtValorTotal.setText(String.format("%.2f", total));

    }

    private void acaoSalvar() {

        if (cbCliente.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione o Cliente.");
            return;
        }

        if (cbFormaPagamento.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione a forma de pagamento.");
            return;
        }

        if (cbTipoConta.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione o tipo da conta.");
            return;
        }

        if (listaProdutos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Adicione pelo menos um produto.");
            return;
        }

        Venda venda = new Venda();

        venda.setDataVenda(LocalDate.parse(txtData.getText()));
        venda.setCliente((Cliente) cbCliente.getSelectedItem());
        venda.setProdutos(listaProdutos);

        for (VendaProduto item : listaProdutos) {
            item.setVenda(venda);
        }

        boolean ok = controller.salvar(
                venda,
                (FormaPagamento) cbFormaPagamento.getSelectedItem(),
                (TipoConta) cbTipoConta.getSelectedItem());

        if (ok) {

            JOptionPane.showMessageDialog(this,
                    "Venda salva com sucesso!");

            limparTela();

        } else {

            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar Venda.");

        }

    }

    private void acaoAlterar() {

        Venda venda = new Venda();

        venda.setId(Integer.parseInt(txtId.getText()));
        venda.setDataVenda(LocalDate.parse(txtData.getText()));
        venda.setCliente((Cliente) cbCliente.getSelectedItem());
        venda.setProdutos(listaProdutos);

        for (VendaProduto item : listaProdutos) {
            item.setVenda(venda);
        }

        if (controller.alterar(venda)) {

            JOptionPane.showMessageDialog(this,
                    "Venda alterada.");

            limparTela();

        } else {

            JOptionPane.showMessageDialog(this,
                    "Erro ao alterar.");

        }

    }

    private void acaoExcluir() {

        if (txtId.getText().isBlank())
            return;

        int op = JOptionPane.showConfirmDialog(this,
                "Deseja excluir esta Venda?");

        if (op != JOptionPane.YES_OPTION)
            return;

        if (controller.excluir(Integer.parseInt(txtId.getText()))) {

            JOptionPane.showMessageDialog(this,
                    "Venda excluída.");

            limparTela();

        } else {

            JOptionPane.showMessageDialog(this,
                    "Erro ao excluir.");

        }

    }

    private void acaoPesquisar() {

        if (txtId.getText().isBlank()) {

            JOptionPane.showMessageDialog(this,
                    "Informe o ID.");

            return;

        }

        Venda venda = controller.pesquisar(Integer.parseInt(txtId.getText()));

        if (venda == null) {

            JOptionPane.showMessageDialog(this,
                    "Venda não encontrada.");

            return;

        }

        txtData.setText(venda.getDataVenda().toString());

        cbCliente.setSelectedItem(venda.getCliente());

        listaProdutos.clear();

        modeloTabela.setRowCount(0);

        for (VendaProduto item : venda.getProdutos()) {

            listaProdutos.add(item);

            modeloTabela.addRow(new Object[] {

                    item.getProduto().getNome(),

                    item.getValorUnitario(),

                    item.getQuantidade(),

                    item.getQuantidade() * item.getValorUnitario()

            });

        }

        calcularValorTotal();

    }

    private void limparTela() {

        txtId.setText("");

        txtData.setText(LocalDate.now().toString());

        txtQuantidade.setText("");

        txtValorUnitario.setText("");

        txtValorTotal.setText("");

        modeloTabela.setRowCount(0);

        listaProdutos.clear();

        cbCliente.setSelectedIndex(-1);
        cbProduto.setSelectedIndex(-1);
        cbFormaPagamento.setSelectedIndex(-1);
        cbTipoConta.setSelectedIndex(-1);

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new TelaVenda().setVisible(true);

        });

    }
}