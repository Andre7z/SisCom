package siscom.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import siscom.controller.ClienteController;
import siscom.model.Cliente;

public class TelaCliente extends JFrame {

    private final JTextField txtId;
    private final JTextField txtNome;
    private final JTextField txtCpf;
    private final JTextField txtRg;
    private final JTextField txtEndereco;
    private final JTextField txtTelefone;

    private final JButton btnSalvar;
    private final JButton btnAlterar;
    private final JButton btnExcluir;
    private final JButton btnPesquisar;
    private final JButton btnImprimir;

    private final JTable tabelaClientes;
    private final DefaultTableModel modeloTabela;

    private final ClienteController clienteController;

    public TelaCliente() {
        setTitle("Tela Cliente");
        setSize(950, 500);
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
        txtId = new JTextField(25);
        painelCampos.add(txtId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        painelCampos.add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        txtNome = new JTextField(25);
        painelCampos.add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        painelCampos.add(new JLabel("CPF:"), gbc);

        gbc.gridx = 1;
        txtCpf = new JTextField(25);
        painelCampos.add(txtCpf, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        painelCampos.add(new JLabel("RG:"), gbc);

        gbc.gridx = 1;
        txtRg = new JTextField(25);
        painelCampos.add(txtRg, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        painelCampos.add(new JLabel("Endereco:"), gbc);

        gbc.gridx = 1;
        txtEndereco = new JTextField(25);
        painelCampos.add(txtEndereco, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        painelCampos.add(new JLabel("Telefone:"), gbc);

        gbc.gridx = 1;
        txtTelefone = new JTextField(25);
        painelCampos.add(txtTelefone, gbc);

        JPanel painelBotoes = new JPanel(new FlowLayout());

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

        modeloTabela = new DefaultTableModel(
                new Object[]{"Id", "Nome", "CPF", "RG", "Endereco", "Telefone"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaClientes = new JTable(modeloTabela);

        tabelaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int linha = tabelaClientes.getSelectedRow();

                    txtId.setText(String.valueOf(modeloTabela.getValueAt(linha, 0)));
                    txtNome.setText(String.valueOf(modeloTabela.getValueAt(linha, 1)));
                    txtCpf.setText(String.valueOf(modeloTabela.getValueAt(linha, 2)));
                    txtRg.setText(String.valueOf(modeloTabela.getValueAt(linha, 3)));
                    txtEndereco.setText(String.valueOf(modeloTabela.getValueAt(linha, 4)));
                    txtTelefone.setText(String.valueOf(modeloTabela.getValueAt(linha, 5)));
                }
            }
        });

        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.add(painelCampos, BorderLayout.NORTH);
        painelCentral.add(new JScrollPane(tabelaClientes), BorderLayout.CENTER);

        add(painelCentral, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        clienteController = new ClienteController();

        btnSalvar.addActionListener(e -> acaoSalvar());
        btnAlterar.addActionListener(e -> acaoAlterar());
        btnExcluir.addActionListener(e -> acaoExcluir());
        btnPesquisar.addActionListener(e -> acaoPesquisar());
        btnImprimir.addActionListener(e -> acaoImprimir());

        carregarTabelaClientes();
    }

    private void acaoSalvar() {
        Cliente cliente = new Cliente();
        cliente.setNome(txtNome.getText());
        cliente.setCpf(txtCpf.getText());
        cliente.setRg(txtRg.getText());
        cliente.setEndereco(txtEndereco.getText());
        cliente.setTelefone(txtTelefone.getText());

        boolean sucesso = clienteController.salvar(cliente);

        if (sucesso) {
            carregarTabelaClientes();
            JOptionPane.showMessageDialog(this, "Cliente salvo com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar cliente.");
        }
    }

    private void acaoAlterar() {
        Cliente cliente = new Cliente();
        cliente.setId(Integer.parseInt(txtId.getText()));
        cliente.setNome(txtNome.getText());
        cliente.setCpf(txtCpf.getText());
        cliente.setRg(txtRg.getText());
        cliente.setEndereco(txtEndereco.getText());
        cliente.setTelefone(txtTelefone.getText());

        boolean sucesso = clienteController.alterar(cliente);

        if (sucesso) {
            carregarTabelaClientes();
            JOptionPane.showMessageDialog(this, "Cliente alterado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao alterar cliente.");
        }
    }

    private void acaoExcluir() {
        boolean sucesso = clienteController.excluir(
                Integer.parseInt(txtId.getText()));

        if (sucesso) {
            txtId.setText("");
            txtNome.setText("");
            txtCpf.setText("");
            txtRg.setText("");
            txtEndereco.setText("");
            txtTelefone.setText("");

            carregarTabelaClientes();
            JOptionPane.showMessageDialog(this, "Cliente excluído!");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao excluir cliente.");
        }
    }

    private void acaoPesquisar() {
        String idTexto = txtId.getText().trim();

        if (!idTexto.isEmpty()) {
            Cliente cliente =
                    clienteController.pesquisar(Integer.parseInt(idTexto));

            if (cliente != null) {
                txtNome.setText(cliente.getNome());
                txtCpf.setText(cliente.getCpf());
                txtRg.setText(cliente.getRg());
                txtEndereco.setText(cliente.getEndereco());
                txtTelefone.setText(cliente.getTelefone());
            } else {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado.");
            }
            return;
        }

        carregarTabelaClientes();
    }

    private void acaoImprimir() {
        JOptionPane.showMessageDialog(this,
                "Implementar relatório com JasperReports");
    }

    private void carregarTabelaClientes() {
        modeloTabela.setRowCount(0);

        List<Cliente> clientes = clienteController.pesquisarTodos();

        for (Cliente cliente : clientes) {
            modeloTabela.addRow(new Object[]{
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getCpf(),
                    cliente.getRg(),
                    cliente.getEndereco(),
                    cliente.getTelefone()
            });
        }
    }
    
        public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCompra().setVisible(true));
    }
}