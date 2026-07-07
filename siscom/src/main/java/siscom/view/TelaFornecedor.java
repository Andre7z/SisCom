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

import siscom.controller.FornecedorController;
import siscom.model.Fornecedor;

public class TelaFornecedor extends JFrame {

    private final JTextField txtId;
    private final JTextField txtNomeFantasia;
    private final JTextField txtRazaoSocial;
    private final JTextField txtCnpj;

    private final JButton btnSalvar;
    private final JButton btnAlterar;
    private final JButton btnExcluir;
    private final JButton btnPesquisar;

    private final JTable tabelaFornecedores;
    private final DefaultTableModel modeloTabela;

    private final FornecedorController fornecedorController;

    public TelaFornecedor() {
        setTitle("Tela Fornecedor");
        setSize(850, 450);
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
        painelCampos.add(new JLabel("Nome Fantasia:"), gbc);

        gbc.gridx = 1;
        txtNomeFantasia = new JTextField(25);
        painelCampos.add(txtNomeFantasia, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        painelCampos.add(new JLabel("Razão Social:"), gbc);

        gbc.gridx = 1;
        txtRazaoSocial = new JTextField(25);
        painelCampos.add(txtRazaoSocial, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        painelCampos.add(new JLabel("CNPJ:"), gbc);

        gbc.gridx = 1;
        txtCnpj = new JTextField(25);
        painelCampos.add(txtCnpj, gbc);

        JPanel painelBotoes = new JPanel(new FlowLayout());

        btnSalvar = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnPesquisar = new JButton("Pesquisar");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnPesquisar);

        modeloTabela = new DefaultTableModel(
                new Object[]{"Id", "Nome Fantasia", "Razão Social", "CNPJ"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaFornecedores = new JTable(modeloTabela);

        tabelaFornecedores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int linha = tabelaFornecedores.getSelectedRow();

                    txtId.setText(String.valueOf(modeloTabela.getValueAt(linha, 0)));
                    txtNomeFantasia.setText(String.valueOf(modeloTabela.getValueAt(linha, 1)));
                    txtRazaoSocial.setText(String.valueOf(modeloTabela.getValueAt(linha, 2)));
                    txtCnpj.setText(String.valueOf(modeloTabela.getValueAt(linha, 3)));
                }
            }
        });

        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.add(painelCampos, BorderLayout.NORTH);
        painelCentral.add(new JScrollPane(tabelaFornecedores), BorderLayout.CENTER);

        add(painelCentral, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        fornecedorController = new FornecedorController();

        btnSalvar.addActionListener(e -> acaoSalvar());
        btnAlterar.addActionListener(e -> acaoAlterar());
        btnExcluir.addActionListener(e -> acaoExcluir());
        btnPesquisar.addActionListener(e -> acaoPesquisar());

        carregarTabelaFornecedores();
    }

    private void acaoSalvar() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNomeFantasia(txtNomeFantasia.getText());
        fornecedor.setRazaoSocial(txtRazaoSocial.getText());
        fornecedor.setCnpj(txtCnpj.getText());

        boolean sucesso = fornecedorController.salvar(fornecedor);

        if (sucesso) {
            carregarTabelaFornecedores();
            JOptionPane.showMessageDialog(this, "Fornecedor salvo com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar fornecedor.");
        }
    }

    private void acaoAlterar() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(Integer.parseInt(txtId.getText()));
        fornecedor.setNomeFantasia(txtNomeFantasia.getText());
        fornecedor.setRazaoSocial(txtRazaoSocial.getText());
        fornecedor.setCnpj(txtCnpj.getText());

        boolean sucesso = fornecedorController.alterar(fornecedor);

        if (sucesso) {
            carregarTabelaFornecedores();
            JOptionPane.showMessageDialog(this, "Fornecedor alterado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao alterar fornecedor.");
        }
    }

    private void acaoExcluir() {
        boolean sucesso = fornecedorController.excluir(
                Integer.parseInt(txtId.getText()));

        if (sucesso) {
            txtId.setText("");
            txtNomeFantasia.setText("");
            txtRazaoSocial.setText("");
            txtCnpj.setText("");

            carregarTabelaFornecedores();
            JOptionPane.showMessageDialog(this, "Fornecedor excluído!");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao excluir fornecedor.");
        }
    }

    private void acaoPesquisar() {
        String idTexto = txtId.getText().trim();

        if (!idTexto.isEmpty()) {
            Fornecedor fornecedor =
                    fornecedorController.pesquisar(Integer.parseInt(idTexto));

            if (fornecedor != null) {
                txtNomeFantasia.setText(fornecedor.getNomeFantasia());
                txtRazaoSocial.setText(fornecedor.getRazaoSocial());
                txtCnpj.setText(fornecedor.getCnpj());
            } else {
                JOptionPane.showMessageDialog(this, "Fornecedor não encontrado.");
            }
            return;
        }

        carregarTabelaFornecedores();
    }


    private void carregarTabelaFornecedores() {
        modeloTabela.setRowCount(0);

        List<Fornecedor> fornecedores =
                fornecedorController.pesquisarTodos();

        for (Fornecedor fornecedor : fornecedores) {
            modeloTabela.addRow(new Object[]{
                    fornecedor.getId(),
                    fornecedor.getNomeFantasia(),
                    fornecedor.getRazaoSocial(),
                    fornecedor.getCnpj()
            });
        }
    }
            public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaFornecedor().setVisible(true));
    }
}