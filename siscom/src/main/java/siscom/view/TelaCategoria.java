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

import siscom.controller.CategoriaController;
import siscom.model.Categoria;

public class TelaCategoria extends JFrame {

    private final JTextField txtId;
    private final JTextField txtNome;
    private final JTextField txtDescricao;

    private final JButton btnSalvar;
    private final JButton btnAlterar;
    private final JButton btnExcluir;
    private final JButton btnPesquisar;
    private final JButton btnImprimir;

    private final JTable tabelaCategorias;
    private final DefaultTableModel modeloTabela;

    private final CategoriaController categoriaController;

    public TelaCategoria() {
        setTitle("Tela Categoria");
        setSize(700, 420);
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
        txtId = new JTextField(22);
        painelCampos.add(txtId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        painelCampos.add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        txtNome = new JTextField(22);
        painelCampos.add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        painelCampos.add(new JLabel("Descricao:"), gbc);

        gbc.gridx = 1;
        txtDescricao = new JTextField(22);
        painelCampos.add(txtDescricao, gbc);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

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
                new Object[] { "Id", "Nome", "Descricao" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaCategorias = new JTable(modeloTabela);

        tabelaCategorias.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int linha = tabelaCategorias.getSelectedRow();

                    if (linha >= 0) {
                        txtId.setText(String.valueOf(modeloTabela.getValueAt(linha, 0)));
                        txtNome.setText(String.valueOf(modeloTabela.getValueAt(linha, 1)));
                        txtDescricao.setText(String.valueOf(modeloTabela.getValueAt(linha, 2)));
                    }
                }
            }
        });

        JPanel painelCentral = new JPanel(new BorderLayout(8, 8));
        painelCentral.add(painelCampos, BorderLayout.NORTH);
        painelCentral.add(new JScrollPane(tabelaCategorias), BorderLayout.CENTER);

        add(painelCentral, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        categoriaController = new CategoriaController();

        btnSalvar.addActionListener(e -> acaoSalvar());
        btnAlterar.addActionListener(e -> acaoAlterar());
        btnExcluir.addActionListener(e -> acaoExcluir());
        btnPesquisar.addActionListener(e -> acaoPesquisar());
        btnImprimir.addActionListener(e -> acaoImprimir());

        carregarTabelaCategorias();
    }

    private void acaoSalvar() {
        Categoria categoria = new Categoria();

        if (!txtId.getText().trim().isEmpty()) {
            categoria.setId(Integer.parseInt(txtId.getText()));
        }

        categoria.setNome(txtNome.getText());
        categoria.setDescricao(txtDescricao.getText());

        boolean sucesso = categoriaController.salvar(categoria);

        if (sucesso) {
            carregarTabelaCategorias();
            JOptionPane.showMessageDialog(this, "Categoria salva com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar categoria.");
        }
    }

    private void acaoAlterar() {
        Categoria categoria = new Categoria();

        categoria.setId(Integer.parseInt(txtId.getText()));
        categoria.setNome(txtNome.getText());
        categoria.setDescricao(txtDescricao.getText());

        boolean sucesso = categoriaController.alterar(categoria);

        if (sucesso) {
            carregarTabelaCategorias();
            JOptionPane.showMessageDialog(this, "Categoria alterada com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao alterar categoria.");
        }
    }

    private void acaoExcluir() {
        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir esta categoria?",
                "Confirmar exclusao",
                JOptionPane.YES_NO_OPTION);

        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }

        boolean sucesso = categoriaController.excluir(
                Integer.parseInt(txtId.getText()));

        if (sucesso) {
            txtId.setText("");
            txtNome.setText("");
            txtDescricao.setText("");
            carregarTabelaCategorias();
            JOptionPane.showMessageDialog(this, "Categoria excluida com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao excluir categoria.");
        }
    }

    private void acaoPesquisar() {
        String idTexto = txtId.getText().trim();

        if (!idTexto.isEmpty()) {
            Categoria categoria = categoriaController.pesquisar(Integer.parseInt(idTexto));

            if (categoria != null) {
                txtNome.setText(categoria.getNome());
                txtDescricao.setText(categoria.getDescricao());
                JOptionPane.showMessageDialog(this, "Categoria encontrada!");
            } else {
                JOptionPane.showMessageDialog(this, "Categoria nao encontrada.");
            }

            return;
        }

        carregarTabelaCategorias();
    }

    private void acaoImprimir() {
        JOptionPane.showMessageDialog(this,
                "Faça o relatório no JasperReports (-|-)");
    }

    private void carregarTabelaCategorias() {
        modeloTabela.setRowCount(0);

        List<Categoria> categorias = categoriaController.pesquisarTodos();

        if (categorias == null) {
            return;
        }

        for (Categoria categoria : categorias) {
            modeloTabela.addRow(new Object[] {
                    categoria.getId(),
                    categoria.getNome(),
                    categoria.getDescricao()
            });
        }
    }

        public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCategoria().setVisible(true));
    }
}