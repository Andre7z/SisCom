package siscom.view;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("SisCom - Menu Principal");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(9, 1, 10, 10));

        JButton btnCategoria = new JButton("Categoria");
        JButton btnCliente = new JButton("Cliente");
        JButton btnFornecedor = new JButton("Fornecedor");
        JButton btnProduto = new JButton("Produto");
        JButton btnCompra = new JButton("Compra");
        JButton btnVenda = new JButton("Venda");
        JButton btnFinanceiro = new JButton("Financeiro");
        JButton btnSair = new JButton("Sair");

        painel.add(btnCategoria);
        painel.add(btnCliente);
        painel.add(btnFornecedor);
        painel.add(btnProduto);
        painel.add(btnCompra);
        painel.add(btnVenda);
        painel.add(btnFinanceiro);
        painel.add(btnSair);

        add(painel, BorderLayout.CENTER);

        btnCategoria.addActionListener(e ->
                new TelaCategoria().setVisible(true));

        btnCliente.addActionListener(e ->
                new TelaCliente().setVisible(true));

        btnFornecedor.addActionListener(e ->
                new TelaFornecedor().setVisible(true));

        btnProduto.addActionListener(e ->
                new TelaProduto().setVisible(true));

        btnCompra.addActionListener(e ->
                new TelaCompra().setVisible(true));

        btnVenda.addActionListener(e ->
                new TelaVenda().setVisible(true));

        btnFinanceiro.addActionListener(e ->
                new TelaFinanceiro().setVisible(true));

        btnSair.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }
}