package siscom.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.hibernate.Session;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import siscom.controller.ClienteController;
import siscom.controller.FornecedorController;
import siscom.dao.Conexao;
import siscom.model.Cliente;
import siscom.model.Fornecedor;

public class TelaRelatorio extends JFrame {

    private JRadioButton rbVenda;
    private JRadioButton rbCompra;
    private JRadioButton rbFinanceiro;

    private JTextField txtDataInicial;
    private JTextField txtDataFinal;

    private JComboBox<Cliente> cbCliente;
    private JComboBox<Fornecedor> cbFornecedor;

    private JButton btnGerar;
    private JButton btnFechar;

    public TelaRelatorio() {

        setTitle("Relatórios");
        setSize(500, 330);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        painel.add(new JLabel("Tipo:"), gbc);

        rbVenda = new JRadioButton("Vendas");
        rbCompra = new JRadioButton("Compras");
        rbFinanceiro = new JRadioButton("Financeiro");

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbVenda);
        grupo.add(rbCompra);
        grupo.add(rbFinanceiro);

        rbVenda.setSelected(true);

        JPanel painelTipo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTipo.add(rbVenda);
        painelTipo.add(rbCompra);
        painelTipo.add(rbFinanceiro);

        gbc.gridx = 1;
        painel.add(painelTipo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(new JLabel("Data Inicial:"), gbc);

        txtDataInicial = new JTextField(12);
        txtDataInicial.setToolTipText("dd/MM/yyyy");

        gbc.gridx = 1;
        painel.add(txtDataInicial, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(new JLabel("Data Final:"), gbc);

        txtDataFinal = new JTextField(12);
        txtDataFinal.setToolTipText("dd/MM/yyyy");

        gbc.gridx = 1;
        painel.add(txtDataFinal, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(new JLabel("Cliente:"), gbc);

        cbCliente = new JComboBox<>();

        gbc.gridx = 1;
        painel.add(cbCliente, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(new JLabel("Fornecedor:"), gbc);

        cbFornecedor = new JComboBox<>();

        gbc.gridx = 1;
        painel.add(cbFornecedor, gbc);

        JPanel botoes = new JPanel();

        btnGerar = new JButton("Gerar Relatório");
        btnFechar = new JButton("Fechar");

        botoes.add(btnGerar);
        botoes.add(btnFechar);

        add(painel, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        carregarClientes();
        carregarFornecedores();
        atualizarCampos();

        rbVenda.addActionListener(e -> atualizarCampos());
        rbCompra.addActionListener(e -> atualizarCampos());
        rbFinanceiro.addActionListener(e -> atualizarCampos());

        btnGerar.addActionListener(e -> gerarRelatorio());

        btnFechar.addActionListener(e -> dispose());
    }

    private void atualizarCampos() {
        cbCliente.setEnabled(rbVenda.isSelected());
        cbFornecedor.setEnabled(rbCompra.isSelected());
    }

    private void carregarClientes() {

        ClienteController controller = new ClienteController();

        cbCliente.removeAllItems();

        for (Cliente c : controller.pesquisarTodos()) {
            cbCliente.addItem(c);
        }
    }

    private void carregarFornecedores() {

        FornecedorController controller = new FornecedorController();

        cbFornecedor.removeAllItems();

        for (Fornecedor f : controller.pesquisarTodos()) {
            cbFornecedor.addItem(f);
        }
    }
    private void gerarRelatorio() {

    String arquivo;

    if (rbVenda.isSelected()) {
        arquivo = "/relatorios/VendaRelatorio.jrxml";
    } else if (rbCompra.isSelected()) {
        arquivo = "/relatorios/CompraRelatorio.jrxml";
    } else {
        arquivo = "/relatorios/FinanceiroRelatorio.jrxml";
    }

    abrirRelatorio(arquivo);
}

private void abrirRelatorio(String arquivo) {

    Session session = null;

    try {

        if (txtDataInicial.getText().trim().isEmpty()
                || txtDataFinal.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Informe a Data Inicial e a Data Final.\nFormato: dd/MM/yyyy");

            return;
        }

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate dataInicial =
                LocalDate.parse(txtDataInicial.getText(), formatter);

        LocalDate dataFinal =
                LocalDate.parse(txtDataFinal.getText(), formatter);

        JasperReport jasperReport =
                JasperCompileManager.compileReport(
                        getClass().getResourceAsStream(arquivo));

        session = Conexao.getSessionFactory().openSession();

        Connection conexao =
                session.doReturningWork(conn -> conn);

        HashMap<String, Object> parametros = new HashMap<>();

        parametros.put(
                "dataInicial",
                java.sql.Date.valueOf(dataInicial));

        parametros.put(
                "dataFinal",
                java.sql.Date.valueOf(dataFinal));

        if (rbVenda.isSelected() && cbCliente.getSelectedItem() != null) {

            Cliente cliente =
                    (Cliente) cbCliente.getSelectedItem();

            parametros.put("clienteId", cliente.getId());

        }

        if (rbCompra.isSelected() && cbFornecedor.getSelectedItem() != null) {

            Fornecedor fornecedor =
                    (Fornecedor) cbFornecedor.getSelectedItem();

            parametros.put("fornecedorId", fornecedor.getId());

        }

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport,
                        parametros,
                        conexao);

        JasperViewer.viewReport(jasperPrint, false);

    } catch (Exception e) {

        e.printStackTrace();

        JOptionPane.showMessageDialog(
                this,
                "Erro ao gerar relatório.\n\n"
                        + e.getMessage());

    } finally {

        if (session != null) {
            session.close();
        }

    }
}
    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {
            new TelaRelatorio().setVisible(true);
        });

    }

}