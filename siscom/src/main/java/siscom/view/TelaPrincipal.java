package siscom.view;

import java.awt.*;
import javax.swing.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("SisCom - Sistema Comercial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);

        criarMenu();
        criarConteudoPrincipal();

        setVisible(true);
    }

    private void criarMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuCadastros = new JMenu("Cadastros");
        JMenu menuLancamentos = new JMenu("Lançamentos");
        JMenu menuSistema = new JMenu("Sistema");

        JMenuItem miCliente = new JMenuItem("Cliente");
        miCliente.addActionListener(e -> new TelaCliente().setVisible(true));

        JMenuItem miFornecedor = new JMenuItem("Fornecedor");
        miFornecedor.addActionListener(e -> new TelaFornecedor().setVisible(true));

        JMenuItem miCategoria = new JMenuItem("Categoria");
        miCategoria.addActionListener(e -> new TelaCategoria().setVisible(true));

        JMenuItem miProduto = new JMenuItem("Produto");
        miProduto.addActionListener(e -> new TelaProduto().setVisible(true));

        JMenuItem miUsuario = new JMenuItem("Usuário");
        miUsuario.addActionListener(e -> new TelaUsuario().setVisible(true));

        JMenuItem miCompra = new JMenuItem("Compra");
        miCompra.addActionListener(e -> new TelaCompra().setVisible(true));

        JMenuItem miVenda = new JMenuItem("Venda");
        miVenda.addActionListener(e -> new TelaVenda().setVisible(true));

        JMenuItem miFinanceiro = new JMenuItem("Financeiro");
        miFinanceiro.addActionListener(e -> new TelaFinanceiro().setVisible(true));

        JMenuItem miSair = new JMenuItem("Sair");
        miSair.addActionListener(e -> System.exit(0));

        menuCadastros.add(miCliente);
        menuCadastros.add(miFornecedor);
        menuCadastros.add(miCategoria);
        menuCadastros.add(miProduto);
        menuCadastros.add(miUsuario);

        menuLancamentos.add(miCompra);
        menuLancamentos.add(miVenda);
        menuLancamentos.add(miFinanceiro);

        menuSistema.add(miSair);

        menuBar.add(menuCadastros);
        menuBar.add(menuLancamentos);
        menuBar.add(menuSistema);

        setJMenuBar(menuBar);
    }

    private void criarConteudoPrincipal() {
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        JPanel painelCentro = new JPanel(new GridBagLayout());
        painelCentro.setBackground(new Color(245, 250, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel lblTitulo = new JLabel("SisCom");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelCentro.add(lblTitulo, gbc);

        JLabel lblSub = new JLabel("Sistema de Controle Comercial");
        lblSub.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 1;
        painelCentro.add(lblSub, gbc);

        JPanel painelBotoes = new JPanel(new GridLayout(2, 4, 20, 20));
        painelBotoes.setPreferredSize(new Dimension(700, 180));

        painelBotoes.add(criarBotao("Clientes", e -> new TelaCliente().setVisible(true)));
        painelBotoes.add(criarBotao("Fornecedores", e -> new TelaFornecedor().setVisible(true)));
        painelBotoes.add(criarBotao("Categorias", e -> new TelaCategoria().setVisible(true)));
        painelBotoes.add(criarBotao("Produtos", e -> new TelaProduto().setVisible(true)));
        painelBotoes.add(criarBotao("Compras", e -> new TelaCompra().setVisible(true)));
        painelBotoes.add(criarBotao("Vendas", e -> new TelaVenda().setVisible(true)));
        painelBotoes.add(criarBotao("Financeiro", e -> new TelaFinanceiro().setVisible(true)));
        painelBotoes.add(criarBotao("Usuários", e -> new TelaUsuario().setVisible(true)));

        gbc.gridy = 2;
        painelCentro.add(painelBotoes, gbc);

        painelPrincipal.add(painelCentro, BorderLayout.CENTER);

        JPanel status = new JPanel(new FlowLayout(FlowLayout.LEFT));
        status.add(new JLabel("Sistema pronto para uso"));

        painelPrincipal.add(status, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private JButton criarBotao(String texto, java.awt.event.ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.setPreferredSize(new Dimension(180, 50));
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.addActionListener(acao);
        return botao;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }
}