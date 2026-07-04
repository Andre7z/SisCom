package siscom.view;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import siscom.controller.CategoriaController;
import siscom.controller.ProdutoController;
import siscom.model.Categoria;
import siscom.model.Produto;

public class TelaProduto extends JFrame {

    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtPreco;
    private JTextField txtQuantidade;
    private JTextField txtUltimaVenda;
    private JTextField txtUltimaCompra;
    private JTextField txtPrecoMedio;

    private JComboBox<Categoria> comboCategoria;

    private JTable tabela;
    private DefaultTableModel modelo;

    private ProdutoController produtoController = new ProdutoController();
    private CategoriaController categoriaController = new CategoriaController();

    public TelaProduto() {
        setTitle("Tela Produto");
        setSize(1000, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel campos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.anchor = GridBagConstraints.WEST;

        txtId = new JTextField(20);
        txtNome = new JTextField(20);
        txtPreco = new JTextField(20);
        txtQuantidade = new JTextField(20);
        txtUltimaVenda = new JTextField(20);
        txtUltimaCompra = new JTextField(20);
        txtPrecoMedio = new JTextField(20);
        comboCategoria = new JComboBox<>();

        int y = 0;

        adicionarCampo(campos, gbc, y++, "Id:", txtId);
        adicionarCampo(campos, gbc, y++, "Nome:", txtNome);
        adicionarCampo(campos, gbc, y++, "Preço:", txtPreco);
        adicionarCampo(campos, gbc, y++, "Quantidade:", txtQuantidade);
        adicionarCampo(campos, gbc, y++, "Última Venda:", txtUltimaVenda);
        adicionarCampo(campos, gbc, y++, "Última Compra:", txtUltimaCompra);
        adicionarCampo(campos, gbc, y++, "Preço Médio:", txtPrecoMedio);

        gbc.gridx = 0;
        gbc.gridy = y;
        campos.add(new JLabel("Categoria:"), gbc);
        gbc.gridx = 1;
        campos.add(comboCategoria, gbc);

        JPanel botoes = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnPesquisar = new JButton("Pesquisar");

        botoes.add(btnSalvar);
        botoes.add(btnAlterar);
        botoes.add(btnExcluir);
        botoes.add(btnPesquisar);

        modelo = new DefaultTableModel(
                new Object[]{"Id","Nome","Preço","Qtd","Categoria"}, 0);

        tabela = new JTable(modelo);

        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int linha = tabela.getSelectedRow();
                    txtId.setText(modelo.getValueAt(linha,0).toString());
                    txtNome.setText(modelo.getValueAt(linha,1).toString());
                    txtPreco.setText(modelo.getValueAt(linha,2).toString());
                    txtQuantidade.setText(modelo.getValueAt(linha,3).toString());
                }
            }
        });

        btnSalvar.addActionListener(e -> salvar());
        btnAlterar.addActionListener(e -> alterar());
        btnExcluir.addActionListener(e -> excluir());
        btnPesquisar.addActionListener(e -> carregarTabela());

        add(campos, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        carregarCategorias();
        carregarTabela();
    }

    private void adicionarCampo(
            JPanel panel, GridBagConstraints gbc,
            int y, String label, JTextField field) {

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void carregarCategorias() {
        comboCategoria.removeAllItems();

        List<Categoria> categorias =
                categoriaController.pesquisarTodos();

        for (Categoria categoria : categorias) {
            comboCategoria.addItem(categoria);
        }
    }

    private Produto montarProduto() {
        Produto produto = new Produto();

        if (!txtId.getText().isBlank()) {
            produto.setId(Integer.parseInt(txtId.getText()));
        }

        produto.setNome(txtNome.getText());
        produto.setPreco(Double.parseDouble(txtPreco.getText()));
        produto.setQuantidade(Integer.parseInt(txtQuantidade.getText()));

        produto.setValorUltimaVenda(
                txtUltimaVenda.getText().isBlank() ?
                        null : Double.parseDouble(txtUltimaVenda.getText()));

        produto.setValorUltimaCompra(
                txtUltimaCompra.getText().isBlank() ?
                        null : Double.parseDouble(txtUltimaCompra.getText()));

        produto.setPrecoMedio(
                txtPrecoMedio.getText().isBlank() ?
                        null : Double.parseDouble(txtPrecoMedio.getText()));

        produto.setCategoria((Categoria) comboCategoria.getSelectedItem());

        return produto;
    }

    private void salvar() {
        produtoController.salvar(montarProduto());
        carregarTabela();
    }

    private void alterar() {
        produtoController.alterar(montarProduto());
        carregarTabela();
    }

    private void excluir() {
        produtoController.excluir(Integer.parseInt(txtId.getText()));
        carregarTabela();
    }

    private void carregarTabela() {
        modelo.setRowCount(0);

        List<Produto> produtos = produtoController.pesquisarTodos();

        for (Produto p : produtos) {
            modelo.addRow(new Object[]{
                    p.getId(),
                    p.getNome(),
                    p.getPreco(),
                    p.getQuantidade(),
                    p.getCategoria() != null ? p.getCategoria().getNome() : ""
            });
        }
    }
}