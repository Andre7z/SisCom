package siscom.view;

import java.awt.*;
import javax.swing.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {

        setTitle("SisCom - Sistema Comercial");
        setSize(1050, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        criarMenu();
        criarConteudo();

        setVisible(true);
    }

    private void criarMenu() {

        JMenuBar menuBar = new JMenuBar();

        JMenu menuCadastros = new JMenu("Cadastros");
        JMenu menuLancamentos = new JMenu("Lançamentos");
        JMenu menuRelatorios = new JMenu("Relatórios");
        JMenu menuSistema = new JMenu("Sistema");

        JMenuItem miCliente = new JMenuItem("Cliente");
        miCliente.addActionListener(e -> new TelaCliente().setVisible(true));

        JMenuItem miFornecedor = new JMenuItem("Fornecedor");
        miFornecedor.addActionListener(e -> new TelaFornecedor().setVisible(true));

        JMenuItem miCategoria = new JMenuItem("Categoria");
        miCategoria.addActionListener(e -> new TelaCategoria().setVisible(true));

        JMenuItem miProduto = new JMenuItem("Produto");
        miProduto.addActionListener(e -> new TelaProduto().setVisible(true));

        JMenuItem miTipoConta = new JMenuItem("Tipo Conta");
        miTipoConta.addActionListener(e -> new TelaTipoConta().setVisible(true));

        JMenuItem miFormaPagamento = new JMenuItem("Forma Pagamento");
        miFormaPagamento.addActionListener(e -> new TelaFormaPagamento().setVisible(true));

        JMenuItem miCompra = new JMenuItem("Compra");
        miCompra.addActionListener(e -> new TelaCompra().setVisible(true));

        JMenuItem miVenda = new JMenuItem("Venda");
        miVenda.addActionListener(e -> new TelaVenda().setVisible(true));

        JMenuItem miFinanceiro = new JMenuItem("Financeiro");
        miFinanceiro.addActionListener(e -> new TelaFinanceiro().setVisible(true));

        JMenuItem miRelCompra = new JMenuItem("Relatório Compras");
        miRelCompra.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Implementar JasperReports"));

        JMenuItem miRelVenda = new JMenuItem("Relatório Vendas");
        miRelVenda.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Implementar JasperReports"));

        JMenuItem miRelFinanceiro = new JMenuItem("Relatório Financeiro");
        miRelFinanceiro.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Implementar JasperReports"));

        JMenuItem miSobre = new JMenuItem("Sobre");
        miSobre.addActionListener(e -> mostrarSobre());

        JMenuItem miSair = new JMenuItem("Sair");
        miSair.addActionListener(e -> System.exit(0));

        menuCadastros.add(miCliente);
        menuCadastros.add(miFornecedor);
        menuCadastros.add(miCategoria);
        menuCadastros.add(miProduto);
        menuCadastros.addSeparator();
        menuCadastros.add(miTipoConta);
        menuCadastros.add(miFormaPagamento);

        menuLancamentos.add(miCompra);
        menuLancamentos.add(miVenda);
        menuLancamentos.add(miFinanceiro);

        menuRelatorios.add(miRelCompra);
        menuRelatorios.add(miRelVenda);
        menuRelatorios.add(miRelFinanceiro);

        menuSistema.add(miSobre);
        menuSistema.addSeparator();
        menuSistema.add(miSair);

        menuBar.add(menuCadastros);
        menuBar.add(menuLancamentos);
        menuBar.add(menuRelatorios);
        menuBar.add(menuSistema);

        setJMenuBar(menuBar);
    }

    private void criarConteudo() {

        JPanel painelPrincipal = new JPanel(new BorderLayout());

        JPanel painelCentro = new JPanel(new GridBagLayout());
        painelCentro.setBackground(new Color(245,250,255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15,15,15,15);

        JLabel titulo = new JLabel("SisCom");
        titulo.setFont(new Font("Arial",Font.BOLD,34));
        titulo.setForeground(new Color(25,25,112));

        gbc.gridx=0;
        gbc.gridy=0;

        painelCentro.add(titulo,gbc);

        JLabel sub = new JLabel("Sistema de Controle Comercial");
        sub.setFont(new Font("Arial",Font.PLAIN,18));

        gbc.gridy=1;

        painelCentro.add(sub,gbc);

        JPanel botoes = new JPanel(new GridLayout(3,4,20,20));
        botoes.setBackground(new Color(245,250,255));
        botoes.setPreferredSize(new Dimension(760,220));

        botoes.add(criarBotao("Clientes",
                e -> new TelaCliente().setVisible(true)));

        botoes.add(criarBotao("Fornecedores",
                e -> new TelaFornecedor().setVisible(true)));

        botoes.add(criarBotao("Categorias",
                e -> new TelaCategoria().setVisible(true)));

        botoes.add(criarBotao("Produtos",
                e -> new TelaProduto().setVisible(true)));

        botoes.add(criarBotao("Compras",
                e -> new TelaCompra().setVisible(true)));

        botoes.add(criarBotao("Vendas",
                e -> new TelaVenda().setVisible(true)));

        botoes.add(criarBotao("Financeiro",
                e -> new TelaFinanceiro().setVisible(true)));

        botoes.add(criarBotao("Tipo Conta",
                e -> new TelaTipoConta().setVisible(true)));

        botoes.add(criarBotao("Forma Pagamento",
                e -> new TelaFormaPagamento().setVisible(true)));

        botoes.add(criarBotao("Relatórios",
                e -> new TelaRelatorio().setVisible(true)));

        botoes.add(criarBotao("Sobre",
                e -> mostrarSobre()));

        botoes.add(criarBotao("Sair",
                e -> System.exit(0)));

        gbc.gridy=2;

        painelCentro.add(botoes,gbc);

        painelPrincipal.add(painelCentro,BorderLayout.CENTER);

        JPanel status = new JPanel(new FlowLayout(FlowLayout.LEFT));
        status.setBorder(BorderFactory.createEtchedBorder());

        status.add(new JLabel("SisCom 1.0 - Sistema pronto para uso"));

        painelPrincipal.add(status,BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private JButton criarBotao(String texto,
                               java.awt.event.ActionListener acao){

        JButton botao = new JButton(texto);

        botao.setPreferredSize(new Dimension(180,55));
        botao.setFont(new Font("Arial",Font.BOLD,14));
        botao.setFocusPainted(false);

        botao.addActionListener(acao);

        return botao;
    }

    private void mostrarSobre(){

        JOptionPane.showMessageDialog(this,
                """
                SisCom
                Versão 1.0

                Sistema Comercial desenvolvido em Java Swing.

                Funcionalidades:
                • Clientes
                • Fornecedores
                • Produtos
                • Compras
                • Vendas
                • Financeiro
                • Relatórios JasperReports
                """,
                "Sobre",
                JOptionPane.INFORMATION_MESSAGE);

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new TelaPrincipal());

    }

}