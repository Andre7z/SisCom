package siscom.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.ArrayList;
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
import siscom.controller.FormaPagamentoController;
import siscom.controller.ProdutoController;
import siscom.controller.TipoContaController;
import siscom.controller.VendaController;
import siscom.model.Cliente;
import siscom.model.FormaPagamento;
import siscom.model.Produto;
import siscom.model.TipoConta;
import siscom.model.Venda;
import siscom.model.VendaProduto;

public class TelaVenda extends JFrame {

    private final JTextField txtId;
    private final JTextField txtDataVenda;
    private final JTextField txtValorTotal;
    private final JTextField txtQuantidade;
    private final JTextField txtValorUnitario;

    private final JComboBox<Cliente> cbCliente;
    private final JComboBox<Produto> cbProduto;
    private final JComboBox<FormaPagamento> cbFormaPagamento;
    private final JComboBox<TipoConta> cbTipoConta;

    private final JButton btnSalvar;
    private final JButton btnExcluir;
    private final JButton btnPesquisar;
    private final JButton btnAdicionarProduto;

    private final JTable tabelaItens;
    private final DefaultTableModel modeloTabela;

    private final VendaController vendaController;
    private final ClienteController clienteController;
    private final ProdutoController produtoController;
    private final FormaPagamentoController formaPagamentoController;
    private final TipoContaController tipoContaController;

    private final List<VendaProduto> itensVenda = new ArrayList<>();

    public TelaVenda() {
        setTitle("Tela Venda");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelCampos.add(new JLabel("Id:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(20);
        painelCampos.add(txtId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        painelCampos.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1;
        cbCliente = new JComboBox<>();
        cbCliente.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Cliente c)
                    setText(c.getNome());
                return this;
            }
        });
        painelCampos.add(cbCliente, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        painelCampos.add(new JLabel("Data:"), gbc);
        gbc.gridx = 1;
        txtDataVenda = new JTextField(20);
        painelCampos.add(txtDataVenda, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        painelCampos.add(new JLabel("Forma Pagamento:"), gbc);
        gbc.gridx = 1;
        cbFormaPagamento = new JComboBox<>();
        painelCampos.add(cbFormaPagamento, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        painelCampos.add(new JLabel("Tipo Conta:"), gbc);
        gbc.gridx = 1;
        cbTipoConta = new JComboBox<>();
        painelCampos.add(cbTipoConta, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        painelCampos.add(new JLabel("Produto:"), gbc);
        gbc.gridx = 1;
        cbProduto = new JComboBox<>();
        painelCampos.add(cbProduto, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        painelCampos.add(new JLabel("Quantidade:"), gbc);
        gbc.gridx = 1;
        txtQuantidade = new JTextField(20);
        painelCampos.add(txtQuantidade, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        painelCampos.add(new JLabel("Valor Unitário:"), gbc);
        gbc.gridx = 1;
        txtValorUnitario = new JTextField(20);
        painelCampos.add(txtValorUnitario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        painelCampos.add(new JLabel("Valor Total:"), gbc);
        gbc.gridx = 1;
        txtValorTotal = new JTextField(20);
        txtValorTotal.setEditable(false);
        painelCampos.add(txtValorTotal, gbc);

        JPanel painelBotoes = new JPanel(new FlowLayout());
        btnAdicionarProduto = new JButton("Adicionar Produto");
        btnSalvar = new JButton("Salvar");
        btnPesquisar = new JButton("Pesquisar");
        btnExcluir = new JButton("Excluir");

        painelBotoes.add(btnAdicionarProduto);
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnPesquisar);
        painelBotoes.add(btnExcluir);

        modeloTabela = new DefaultTableModel(
                new Object[] { "Produto", "Quantidade", "Valor Unitário", "Subtotal" }, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tabelaItens = new JTable(modeloTabela);

        add(painelCampos, BorderLayout.NORTH);
        add(new JScrollPane(tabelaItens), BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        vendaController = new VendaController();
        clienteController = new ClienteController();
        produtoController = new ProdutoController();
        formaPagamentoController = new FormaPagamentoController();
        tipoContaController = new TipoContaController();

        carregarCombos();

        btnAdicionarProduto.addActionListener(e -> adicionarProduto());
        btnSalvar.addActionListener(e -> salvarVenda());
        btnPesquisar.addActionListener(e -> pesquisarVenda());
        btnExcluir.addActionListener(e -> excluirVenda());
    }

    private void carregarCombos() {
        for (Cliente c : clienteController.pesquisarTodos())
            cbCliente.addItem(c);
        for (Produto p : produtoController.pesquisarTodos())
            cbProduto.addItem(p);
        for (FormaPagamento f : formaPagamentoController.pesquisarTodos())
            cbFormaPagamento.addItem(f);
        for (TipoConta t : tipoContaController.pesquisarTodos())
            cbTipoConta.addItem(t);
    }

    private void adicionarProduto() {
        Produto produto = (Produto) cbProduto.getSelectedItem();
        int quantidade = Integer.parseInt(txtQuantidade.getText());
        double valor = Double.parseDouble(txtValorUnitario.getText());

        VendaProduto vp = new VendaProduto();
        vp.setProduto(produto);
        vp.setQuantidade(quantidade);
        vp.setValorUnitario(valor);

        itensVenda.add(vp);

        modeloTabela.addRow(new Object[] {
                produto.getNome(),
                quantidade,
                valor,
                quantidade * valor
        });

        recalcularTotal();
    }

    private void recalcularTotal() {
        double total = 0;
        for (VendaProduto vp : itensVenda)
            total += vp.getQuantidade() * vp.getValorUnitario();
        txtValorTotal.setText(String.valueOf(total));
    }

    private void salvarVenda() {
        Venda venda = new Venda();
        if (!txtId.getText().isBlank())
            venda.setId(Integer.parseInt(txtId.getText()));

        venda.setCliente((Cliente) cbCliente.getSelectedItem());
        venda.setDataVenda(LocalDate.parse(txtDataVenda.getText()));
        venda.setVendaProdutos(itensVenda);

        boolean ok = vendaController.salvar(
                venda,
                (FormaPagamento) cbFormaPagamento.getSelectedItem(),
                (TipoConta) cbTipoConta.getSelectedItem());

        JOptionPane.showMessageDialog(this,
                ok ? "Venda salva!" : "Erro ao salvar");
    }

    private void pesquisarVenda() {
        Venda venda = vendaController.pesquisar(Integer.parseInt(txtId.getText()));
        if (venda == null) {
            JOptionPane.showMessageDialog(this, "Venda não encontrada");
            return;
        }

        txtDataVenda.setText(venda.getDataVenda().toString());
        txtValorTotal.setText(String.valueOf(venda.getValorTotal()));
    }

private void excluirVenda() {
    boolean ok = vendaController.excluir(Integer.parseInt(txtId.getText()));
    JOptionPane.showMessageDialog(this,
        ok ? "Venda excluída!" : "Erro ao excluir");
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaVenda().setVisible(true));
    }
}