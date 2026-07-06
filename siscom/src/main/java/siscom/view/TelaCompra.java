package siscom.view;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import siscom.controller.CompraController;
import siscom.controller.FornecedorController;
import siscom.controller.ProdutoController;
import siscom.model.Compra;
import siscom.model.CompraProduto;
import siscom.model.Fornecedor;
import siscom.model.Produto;

public class TelaCompra extends JFrame {

    private JTextField txtId;
    private JTextField txtData;
    private JTextField txtValorUnitario;
    private JTextField txtQuantidade;
    private JTextField txtValorTotal;

    private JComboBox<Fornecedor> cbFornecedor;
    private JComboBox<Produto> cbProduto;

    private JButton btnAdicionar;
    private JButton btnRemover;

    private JButton btnSalvar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnPesquisar;
    private JButton btnImprimir;

    private JTable tabelaProdutos;
    private DefaultTableModel modeloTabela;

    private CompraController controller;
    private ProdutoController produtoController;
    private FornecedorController fornecedorController;

    private java.util.List<CompraProduto> listaProdutos;

    public TelaCompra() {

        setTitle("Tela Compra");
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        controller = new CompraController();
        produtoController = new ProdutoController();
        fornecedorController = new FornecedorController();

        listaProdutos = new ArrayList<>();

        JPanel painelCampos = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        //----------------------------

        gbc.gridx=0;
        gbc.gridy=0;
        painelCampos.add(new JLabel("Id:"),gbc);

        gbc.gridx=1;
        txtId = new JTextField(10);
        painelCampos.add(txtId,gbc);

        //----------------------------

        gbc.gridx=2;
        painelCampos.add(new JLabel("Data:"),gbc);

        gbc.gridx=3;
        txtData = new JTextField(10);
        txtData.setText(LocalDate.now().toString());
        painelCampos.add(txtData,gbc);

        //----------------------------

        gbc.gridx=0;
        gbc.gridy=1;
        painelCampos.add(new JLabel("Fornecedor:"),gbc);

        gbc.gridx=1;
        gbc.gridwidth=3;

        cbFornecedor = new JComboBox<>();
        cbFornecedor.setPreferredSize(new Dimension(300,25));

        painelCampos.add(cbFornecedor,gbc);

        gbc.gridwidth=1;

        //----------------------------

        gbc.gridx=0;
        gbc.gridy=2;
        painelCampos.add(new JLabel("Produto:"),gbc);

        gbc.gridx=1;

        cbProduto = new JComboBox<>();
        cbProduto.setPreferredSize(new Dimension(250,25));

        painelCampos.add(cbProduto,gbc);

        //----------------------------

        gbc.gridx=2;
        painelCampos.add(new JLabel("Valor Unitário:"),gbc);

        gbc.gridx=3;

        txtValorUnitario = new JTextField(8);

        painelCampos.add(txtValorUnitario,gbc);

        //----------------------------

        gbc.gridx=0;
        gbc.gridy=3;

        painelCampos.add(new JLabel("Quantidade:"),gbc);

        gbc.gridx=1;

        txtQuantidade = new JTextField(8);

        painelCampos.add(txtQuantidade,gbc);

        //----------------------------

        btnAdicionar = new JButton("Adicionar Produto");
        btnRemover = new JButton("Remover Produto");

        JPanel painelAdd = new JPanel();

        painelAdd.add(btnAdicionar);
        painelAdd.add(btnRemover);

        gbc.gridx=2;
        gbc.gridwidth=2;

        painelCampos.add(painelAdd,gbc);

        gbc.gridwidth=1;

        //--------------------------------------------------

        modeloTabela = new DefaultTableModel(
                new Object[]{
                        "Produto",
                        "Valor Unit.",
                        "Quantidade",
                        "Subtotal"
                },0){

            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }

        };

        tabelaProdutos = new JTable(modeloTabela);

        JScrollPane scroll = new JScrollPane(tabelaProdutos);

        //--------------------------------------------------

        JPanel painelSul = new JPanel(new BorderLayout());

        JPanel painelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        painelTotal.add(new JLabel("Valor Total:"));

        txtValorTotal = new JTextField(10);
        txtValorTotal.setEditable(false);

        painelTotal.add(txtValorTotal);

        JPanel painelBotoes = new JPanel();

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

        painelSul.add(painelTotal,BorderLayout.NORTH);
        painelSul.add(painelBotoes,BorderLayout.SOUTH);

        add(painelCampos,BorderLayout.NORTH);
        add(scroll,BorderLayout.CENTER);
        add(painelSul,BorderLayout.SOUTH);
                carregarFornecedores();
        carregarProdutos();

        cbProduto.addActionListener(e -> preencherValorUnitario());

        btnAdicionar.addActionListener(e -> adicionarProduto());

        btnRemover.addActionListener(e -> removerProduto());
    }

    private void carregarFornecedores() {

        cbFornecedor.removeAllItems();

        for (Fornecedor fornecedor : fornecedorController.pesquisarTodos()) {
            cbFornecedor.addItem(fornecedor);
        }

    }

    private void carregarProdutos() {

        cbProduto.removeAllItems();

        for (Produto produto : produtoController.pesquisarTodos()) {
            cbProduto.addItem(produto);
        }

    }

    private void preencherValorUnitario() {

        Produto produto = (Produto) cbProduto.getSelectedItem();

        if (produto != null && produto.getPreco() != null) {

            txtValorUnitario.setText(String.valueOf(produto.getPreco()));

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

        int quantidade = Integer.parseInt(txtQuantidade.getText());

        double valorUnitario =
                Double.parseDouble(txtValorUnitario.getText());

        double subtotal = quantidade * valorUnitario;

        CompraProduto item = new CompraProduto();

        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setValorUnitario(valorUnitario);

        listaProdutos.add(item);

        modeloTabela.addRow(new Object[]{

                produto.getNome(),
                valorUnitario,
                quantidade,
                subtotal

        });

        calcularValorTotal();

        txtQuantidade.setText("");

    }

    private void removerProduto() {

        int linha = tabelaProdutos.getSelectedRow();

        if (linha < 0) {

            JOptionPane.showMessageDialog(this,
                    "Selecione um produto.");

            return;

        }

        listaProdutos.remove(linha);

        modeloTabela.removeRow(linha);

        calcularValorTotal();

    }

    private void calcularValorTotal() {

        double total = 0;

        for (int i = 0; i < modeloTabela.getRowCount(); i++) {

            total += Double.parseDouble(
                    modeloTabela.getValueAt(i,3).toString());

        }

        txtValorTotal.setText(String.valueOf(total));

    }