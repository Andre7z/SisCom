package siscom.view;

import siscom.controller.CategoriaController;
import siscom.controller.ProdutoController;
import siscom.model.Categoria;
import siscom.model.Produto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaProduto extends JFrame {

    private JComboBox<Produto> cbProdutos;
    private JTextField txtNome;
    private JTextField txtPreco;
    private JTextField txtQuantidade;
    private JComboBox<Categoria> cbCategoria;

    private JButton btnSalvar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnLimpar;

    private ProdutoController controller;
    private CategoriaController categoriaController;

    public TelaProduto() {
        controller = new ProdutoController();
        categoriaController = new CategoriaController();

        setTitle("Cadastro de Produtos");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
        carregarProdutos();
        carregarCategorias();
    }

    private void initComponents() {
        JPanel painel = new JPanel(new GridLayout(7, 2, 5, 5));

        painel.add(new JLabel("Pesquisar Produto:"));
        cbProdutos = new JComboBox<>();
        cbProdutos.addActionListener(e -> carregarProdutoSelecionado());
        painel.add(cbProdutos);

        painel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painel.add(txtNome);

        painel.add(new JLabel("Preço:"));
        txtPreco = new JTextField();
        painel.add(txtPreco);

        painel.add(new JLabel("Quantidade:"));
        txtQuantidade = new JTextField();
        painel.add(txtQuantidade);

        painel.add(new JLabel("Categoria:"));
        cbCategoria = new JComboBox<>();
        painel.add(cbCategoria);

        btnSalvar = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnLimpar = new JButton("Limpar");

        btnSalvar.addActionListener(e -> salvar());
        btnAlterar.addActionListener(e -> alterar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limparCampos());

        painel.add(btnSalvar);
        painel.add(btnAlterar);
        painel.add(btnExcluir);
        painel.add(btnLimpar);

        add(painel);
    }

    private void carregarProdutos() {
        cbProdutos.removeAllItems();

        List<Produto> produtos = controller.pesquisarTodos();

        for (Produto p : produtos) {
            cbProdutos.addItem(p);
        }
    }

    private void carregarCategorias() {
        cbCategoria.removeAllItems();

        List<Categoria> categorias = categoriaController.pesquisarTodos();

        for (Categoria c : categorias) {
            cbCategoria.addItem(c);
        }
    }

    private void carregarProdutoSelecionado() {
        Produto produto = (Produto) cbProdutos.getSelectedItem();

        if (produto != null) {
            txtNome.setText(produto.getNome());

            if (produto.getPreco() != null)
                txtPreco.setText(produto.getPreco().toString());

            if (produto.getQuantidade() != null)
                txtQuantidade.setText(produto.getQuantidade().toString());

            cbCategoria.setSelectedItem(produto.getCategoria());
        }
    }

    private void salvar() {
        try {
            Produto produto = new Produto();

            produto.setNome(txtNome.getText());
            produto.setPreco(Double.parseDouble(txtPreco.getText()));
            produto.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
            produto.setCategoria((Categoria) cbCategoria.getSelectedItem());

            if (controller.salvar(produto)) {
                JOptionPane.showMessageDialog(this, "Salvo com sucesso");
                limparCampos();
                carregarProdutos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Dados inválidos");
        }
    }

    private void alterar() {
        try {
            Produto produto = (Produto) cbProdutos.getSelectedItem();

            if (produto == null) return;

            produto.setNome(txtNome.getText());
            produto.setPreco(Double.parseDouble(txtPreco.getText()));
            produto.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
            produto.setCategoria((Categoria) cbCategoria.getSelectedItem());

            if (controller.alterar(produto)) {
                JOptionPane.showMessageDialog(this, "Alterado com sucesso");
                carregarProdutos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao alterar");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Dados inválidos");
        }
    }

    private void excluir() {
        Produto produto = (Produto) cbProdutos.getSelectedItem();

        if (produto == null) return;

        if (controller.excluir(produto.getId())) {
            JOptionPane.showMessageDialog(this, "Excluído com sucesso");
            limparCampos();
            carregarProdutos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao excluir");
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtPreco.setText("");
        txtQuantidade.setText("");
        cbProdutos.setSelectedItem(null);
        cbCategoria.setSelectedItem(null);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new TelaProduto().setVisible(true)
        );
    }
}