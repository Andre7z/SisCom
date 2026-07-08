package siscom.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import siscom.controller.FinanceiroController;
import siscom.controller.FinanceiroParcelaController;
import siscom.model.Financeiro;
import siscom.model.FinanceiroParcela;

public class TelaFinanceiroParcela extends JFrame {

    private JTextField txtId;
    private JComboBox<Financeiro> cbFinanceiro;
    private JTextField txtNParcela;
    private JTextField txtDataVencimento;
    private JTextField txtDataPagamento;
    private JTextField txtValorOriginal;
    private JTextField txtDesconto;
    private JTextField txtAcrescimo;
    private JTextField txtValorFinal;
    private JComboBox<String> cbStatus;

    private JButton btnSalvar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnPesquisar;
    private JButton btnLimpar;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private FinanceiroParcelaController controller = new FinanceiroParcelaController();
    private FinanceiroController financeiroController = new FinanceiroController();

    private final Financeiro financeiroFiltro;

    private static final String[] STATUS = {"Pendente", "Pago", "Atrasado"};

    public TelaFinanceiroParcela() {
        this(null);
    }

    public TelaFinanceiroParcela(Financeiro financeiroPreSelecionado) {

        this.financeiroFiltro = financeiroPreSelecionado;

        setTitle(financeiroFiltro != null
                ? "Parcelas do Financeiro Id " + financeiroFiltro.getId()
                : "Cadastro de Parcelas do Financeiro");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel painelCampos = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        painelCampos.add(new JLabel("Id"), gbc);

        gbc.gridx = 1;
        txtId = new JTextField(6);
        txtId.setEditable(false);
        painelCampos.add(txtId, gbc);

        gbc.gridx = 2;
        painelCampos.add(new JLabel("Financeiro"), gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 2;
        cbFinanceiro = new JComboBox<>();
        painelCampos.add(cbFinanceiro, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        painelCampos.add(new JLabel("Nº Parcela"), gbc);

        gbc.gridx = 1;
        txtNParcela = new JTextField(6);
        painelCampos.add(txtNParcela, gbc);

        gbc.gridx = 2;
        painelCampos.add(new JLabel("Status"), gbc);

        gbc.gridx = 3;
        cbStatus = new JComboBox<>(STATUS);
        painelCampos.add(cbStatus, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        painelCampos.add(new JLabel("Data Vencimento"), gbc);

        gbc.gridx = 1;
        txtDataVencimento = new JTextField(10);
        txtDataVencimento.setText(LocalDate.now().toString());
        painelCampos.add(txtDataVencimento, gbc);

        gbc.gridx = 2;
        painelCampos.add(new JLabel("Data Pagamento"), gbc);

        gbc.gridx = 3;
        txtDataPagamento = new JTextField(10);
        painelCampos.add(txtDataPagamento, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        painelCampos.add(new JLabel("Valor Original"), gbc);

        gbc.gridx = 1;
        txtValorOriginal = new JTextField(10);
        painelCampos.add(txtValorOriginal, gbc);

        gbc.gridx = 2;
        painelCampos.add(new JLabel("Desconto"), gbc);

        gbc.gridx = 3;
        txtDesconto = new JTextField(10);
        txtDesconto.setText("0");
        painelCampos.add(txtDesconto, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        painelCampos.add(new JLabel("Acréscimo"), gbc);

        gbc.gridx = 1;
        txtAcrescimo = new JTextField(10);
        txtAcrescimo.setText("0");
        painelCampos.add(txtAcrescimo, gbc);

        gbc.gridx = 2;
        painelCampos.add(new JLabel("Valor Final"), gbc);

        gbc.gridx = 3;
        txtValorFinal = new JTextField(10);
        txtValorFinal.setEditable(false);
        painelCampos.add(txtValorFinal, gbc);

        JButton btnCalcular = new JButton("Calcular Valor Final");
        gbc.gridx = 4;
        gbc.gridy = 4;
        painelCampos.add(btnCalcular, gbc);

        btnCalcular.addActionListener(e -> calcularValorFinal());

        JPanel painelBotoes = new JPanel(new GridLayout(1, 5, 10, 10));

        btnSalvar = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnPesquisar = new JButton("Pesquisar");
        btnLimpar = new JButton("Limpar");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnPesquisar);
        painelBotoes.add(btnLimpar);

        modeloTabela = new DefaultTableModel(
                new Object[] {
                        "ID",
                        "Financeiro",
                        "Nº Parcela",
                        "Vencimento",
                        "Pagamento",
                        "Valor Final",
                        "Status"
                }, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };

        tabela = new JTable(modeloTabela);

        tabela.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {

                    int linha = tabela.getSelectedRow();

                    if (linha == -1) {
                        return;
                    }

                    Integer id = (Integer) modeloTabela.getValueAt(linha, 0);

                    carregarParcela(id);

                }

            }

        });

        JScrollPane scroll = new JScrollPane(tabela);

        add(painelCampos, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        configurarRenderizador();

        carregarFinanceiros();

        if (financeiroPreSelecionado != null) {
            cbFinanceiro.setSelectedItem(financeiroPreSelecionado);
        }

        carregarTabela();

        btnSalvar.addActionListener(e -> salvar());
        btnAlterar.addActionListener(e -> alterar());
        btnExcluir.addActionListener(e -> excluir());
        btnPesquisar.addActionListener(e -> {

            if (txtId.getText().isBlank()) {
                JOptionPane.showMessageDialog(this,
                        "Informe o Id ou dê duplo clique numa linha da tabela.");
                return;
            }

            try {
                carregarParcela(Integer.parseInt(txtId.getText().trim()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Id inválido.");
            }

        });
        btnLimpar.addActionListener(e -> limpar());

    }

    private void configurarRenderizador() {

        cbFinanceiro.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                    Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {

                super.getListCellRendererComponent(list, null, index,
                        isSelected, cellHasFocus);

                if (value instanceof Financeiro financeiro) {

                    String tipo = financeiro.getPagarOuReceber() == 0
                            ? "Pagar"
                            : "Receber";

                    setText("Id " + financeiro.getId() + " - "
                            + financeiro.getDataConta() + " - " + tipo);
                } else {
                    setText("");
                }

                return this;
            }
        });

    }

    private void carregarFinanceiros() {

        cbFinanceiro.removeAllItems();

        List<Financeiro> lista = financeiroController.pesquisarTodos();

        for (Financeiro financeiro : lista) {
            cbFinanceiro.addItem(financeiro);
        }

    }

    private void carregarTabela() {

        modeloTabela.setRowCount(0);

        List<FinanceiroParcela> lista = controller.pesquisarTodos();

        for (FinanceiroParcela parcela : lista) {

            if (financeiroFiltro != null
                    && (parcela.getFinanceiro() == null
                        || parcela.getFinanceiro().getId() != financeiroFiltro.getId())) {
                continue;
            }

            String financeiroDesc = parcela.getFinanceiro() != null
                    ? "Id " + parcela.getFinanceiro().getId()
                    : "-";

            String status = parcela.getStatus() >= 0
                    && parcela.getStatus() < STATUS.length
                    ? STATUS[parcela.getStatus()]
                    : String.valueOf(parcela.getStatus());

            modeloTabela.addRow(new Object[] {
                    parcela.getId(),
                    financeiroDesc,
                    parcela.getNParcela(),
                    parcela.getDataVencimento(),
                    parcela.getDataPagamento() != null
                            ? parcela.getDataPagamento() : "-",
                    parcela.getValorFinal(),
                    status
            });

        }

    }

    private void calcularValorFinal() {

        try {

            double original = Double.parseDouble(
                    txtValorOriginal.getText().trim().replace(",", "."));

            double desconto = txtDesconto.getText().isBlank()
                    ? 0
                    : Double.parseDouble(
                            txtDesconto.getText().trim().replace(",", "."));

            double acrescimo = txtAcrescimo.getText().isBlank()
                    ? 0
                    : Double.parseDouble(
                            txtAcrescimo.getText().trim().replace(",", "."));

            double valorFinal = original - desconto + acrescimo;

            txtValorFinal.setText(String.format("%.2f", valorFinal));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Informe valores numéricos válidos para calcular.");
        }

    }

    private boolean validarCampos() {

        if (cbFinanceiro.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione o financeiro.");
            return false;
        }

        if (txtNParcela.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Informe o número da parcela.");
            return false;
        }

        if (txtValorOriginal.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Informe o valor original.");
            return false;
        }

        return true;
    }

    private void salvar() {

        if (!validarCampos()) {
            return;
        }

        LocalDate dataVencimento;

        try {
            dataVencimento = LocalDate.parse(txtDataVencimento.getText().trim());
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "Data de vencimento inválida. Use o formato AAAA-MM-DD.");
            return;
        }

        LocalDate dataPagamento = null;

        if (!txtDataPagamento.getText().isBlank()) {
            try {
                dataPagamento = LocalDate.parse(txtDataPagamento.getText().trim());
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this,
                        "Data de pagamento inválida. Use o formato AAAA-MM-DD.");
                return;
            }
        }

        int nParcela;
        double valorOriginal;
        double desconto;
        double acrescimo;

        try {
            nParcela = Integer.parseInt(txtNParcela.getText().trim());
            valorOriginal = Double.parseDouble(
                    txtValorOriginal.getText().trim().replace(",", "."));
            desconto = txtDesconto.getText().isBlank()
                    ? 0
                    : Double.parseDouble(
                            txtDesconto.getText().trim().replace(",", "."));
            acrescimo = txtAcrescimo.getText().isBlank()
                    ? 0
                    : Double.parseDouble(
                            txtAcrescimo.getText().trim().replace(",", "."));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Verifique os valores numéricos informados.");
            return;
        }

        FinanceiroParcela parcela = new FinanceiroParcela();

        parcela.setFinanceiro((Financeiro) cbFinanceiro.getSelectedItem());
        parcela.setNParcela(nParcela);
        parcela.setDataVencimento(dataVencimento);
        parcela.setDataPagamento(dataPagamento);
        parcela.setValorOriginal(valorOriginal);
        parcela.setDesconto(desconto);
        parcela.setAcrescimo(acrescimo);
        parcela.setValorFinal(valorOriginal - desconto + acrescimo);
        parcela.setStatus(cbStatus.getSelectedIndex());

        boolean sucesso = controller.salvar(parcela);

        if (sucesso) {

            JOptionPane.showMessageDialog(this,
                    "Parcela salva com sucesso! Id gerado: " + parcela.getId());

            limpar();
            carregarTabela();

        } else {

            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar parcela. Verifique os dados informados.");

        }

    }

    private void alterar() {

        if (txtId.getText().isBlank()) {
            JOptionPane.showMessageDialog(this,
                    "Pesquise uma parcela antes de alterar.");
            return;
        }

        if (!validarCampos()) {
            return;
        }

        int id;

        try {
            id = Integer.parseInt(txtId.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Id inválido.");
            return;
        }

        LocalDate dataVencimento;

        try {
            dataVencimento = LocalDate.parse(txtDataVencimento.getText().trim());
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "Data de vencimento inválida. Use o formato AAAA-MM-DD.");
            return;
        }

        LocalDate dataPagamento = null;

        if (!txtDataPagamento.getText().isBlank()) {
            try {
                dataPagamento = LocalDate.parse(txtDataPagamento.getText().trim());
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this,
                        "Data de pagamento inválida. Use o formato AAAA-MM-DD.");
                return;
            }
        }

        int nParcela;
        double valorOriginal;
        double desconto;
        double acrescimo;

        try {
            nParcela = Integer.parseInt(txtNParcela.getText().trim());
            valorOriginal = Double.parseDouble(
                    txtValorOriginal.getText().trim().replace(",", "."));
            desconto = txtDesconto.getText().isBlank()
                    ? 0
                    : Double.parseDouble(
                            txtDesconto.getText().trim().replace(",", "."));
            acrescimo = txtAcrescimo.getText().isBlank()
                    ? 0
                    : Double.parseDouble(
                            txtAcrescimo.getText().trim().replace(",", "."));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Verifique os valores numéricos informados.");
            return;
        }

        // Busca a parcela existente para não perder o vínculo já salvo
        FinanceiroParcela parcela = controller.pesquisar(id);

        if (parcela == null) {
            JOptionPane.showMessageDialog(this, "Parcela não encontrada.");
            return;
        }

        parcela.setFinanceiro((Financeiro) cbFinanceiro.getSelectedItem());
        parcela.setNParcela(nParcela);
        parcela.setDataVencimento(dataVencimento);
        parcela.setDataPagamento(dataPagamento);
        parcela.setValorOriginal(valorOriginal);
        parcela.setDesconto(desconto);
        parcela.setAcrescimo(acrescimo);
        parcela.setValorFinal(valorOriginal - desconto + acrescimo);
        parcela.setStatus(cbStatus.getSelectedIndex());

        boolean sucesso = controller.alterar(parcela);

        if (sucesso) {

            JOptionPane.showMessageDialog(this, "Parcela alterada com sucesso!");

            limpar();
            carregarTabela();

        } else {

            JOptionPane.showMessageDialog(this, "Erro ao alterar parcela.");

        }

    }

    private void excluir() {

        if (txtId.getText().isBlank()) {
            JOptionPane.showMessageDialog(this,
                    "Pesquise uma parcela antes de excluir.");
            return;
        }

        int op = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir esta parcela?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION);

        if (op != JOptionPane.YES_OPTION) {
            return;
        }

        int id;

        try {
            id = Integer.parseInt(txtId.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Id inválido.");
            return;
        }

        boolean sucesso = controller.excluir(id);

        if (sucesso) {

            JOptionPane.showMessageDialog(this, "Parcela excluída com sucesso!");

            limpar();
            carregarTabela();

        } else {

            JOptionPane.showMessageDialog(this, "Erro ao excluir parcela.");

        }

    }

    private void carregarParcela(int id) {

        FinanceiroParcela parcela = controller.pesquisar(id);

        if (parcela == null) {
            JOptionPane.showMessageDialog(this, "Parcela não encontrada.");
            return;
        }

        txtId.setText(String.valueOf(parcela.getId()));
        cbFinanceiro.setSelectedItem(parcela.getFinanceiro());
        txtNParcela.setText(String.valueOf(parcela.getNParcela()));
        txtDataVencimento.setText(parcela.getDataVencimento().toString());
        txtDataPagamento.setText(parcela.getDataPagamento() != null
                ? parcela.getDataPagamento().toString() : "");
        txtValorOriginal.setText(String.valueOf(parcela.getValorOriginal()));
        txtDesconto.setText(String.valueOf(parcela.getDesconto()));
        txtAcrescimo.setText(String.valueOf(parcela.getAcrescimo()));
        txtValorFinal.setText(String.valueOf(parcela.getValorFinal()));

        int status = parcela.getStatus();
        cbStatus.setSelectedIndex(status >= 0 && status < STATUS.length ? status : 0);

    }

    private void limpar() {

        txtId.setText("");
        cbFinanceiro.setSelectedIndex(-1);
        txtNParcela.setText("");
        txtDataVencimento.setText(LocalDate.now().toString());
        txtDataPagamento.setText("");
        txtValorOriginal.setText("");
        txtDesconto.setText("0");
        txtAcrescimo.setText("0");
        txtValorFinal.setText("");
        cbStatus.setSelectedIndex(0);

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new TelaFinanceiroParcela().setVisible(true));

    }
}