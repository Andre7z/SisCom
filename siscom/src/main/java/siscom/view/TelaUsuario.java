package siscom.view;

import java.awt.*;
import javax.swing.*;

import siscom.controller.UsuarioController;
import siscom.model.Usuario;

public class TelaUsuario extends JFrame {

    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JTextField txtPerfil;

    private JButton btnSalvar;
    private JButton btnPesquisar;
    private JButton btnExcluir;
    private JButton btnRelatorio;

    private UsuarioController controller;

    public TelaUsuario() {
        controller = new UsuarioController();

        setTitle("Cadastro de Usuário");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painel = new JPanel(new GridLayout(5, 2, 5, 5));

        painel.add(new JLabel("ID:"));
        txtId = new JTextField();
        painel.add(txtId);

        painel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painel.add(txtNome);

        painel.add(new JLabel("Login:"));
        txtLogin = new JTextField();
        painel.add(txtLogin);

        painel.add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        painel.add(txtSenha);

        painel.add(new JLabel("Perfil:"));
        txtPerfil = new JTextField();
        painel.add(txtPerfil);

        JPanel botoes = new JPanel();

        btnSalvar = new JButton("Salvar");
        btnPesquisar = new JButton("Pesquisar");
        btnExcluir = new JButton("Excluir");
        btnRelatorio = new JButton("Relatório");

        botoes.add(btnSalvar);
        botoes.add(btnPesquisar);
        botoes.add(btnExcluir);
        botoes.add(btnRelatorio);

        add(painel, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> salvar());
        btnPesquisar.addActionListener(e -> pesquisar());
        btnExcluir.addActionListener(e -> excluir());

        btnRelatorio.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Relatório ainda não implementado."));
    }

    private void salvar() {
        Usuario usuario = new Usuario();

        if (!txtId.getText().isBlank()) {
            usuario.setId(Integer.parseInt(txtId.getText()));
        }

        usuario.setNome(txtNome.getText());
        usuario.setLogin(txtLogin.getText());
        usuario.setSenha(new String(txtSenha.getPassword()));
        usuario.setPerfil(txtPerfil.getText());

        boolean ok;

        if (usuario.getId() == null) {
            ok = controller.salvar(usuario);
        } else {
            ok = controller.alterar(usuario);
        }

        if (ok) {
            JOptionPane.showMessageDialog(this, "Salvo com sucesso!");
            limparCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar.");
        }
    }

    private void pesquisar() {
        if (txtId.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Informe o ID");
            return;
        }

        Usuario usuario =
                controller.pesquisar(Integer.parseInt(txtId.getText()));

        if (usuario != null) {
            txtNome.setText(usuario.getNome());
            txtLogin.setText(usuario.getLogin());
            txtSenha.setText(usuario.getSenha());
            txtPerfil.setText(usuario.getPerfil());
        } else {
            JOptionPane.showMessageDialog(this, "Usuário não encontrado.");
        }
    }

    private void excluir() {
        if (txtId.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Informe o ID");
            return;
        }

        boolean ok =
                controller.excluir(Integer.parseInt(txtId.getText()));

        if (ok) {
            JOptionPane.showMessageDialog(this, "Excluído com sucesso!");
            limparCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao excluir.");
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtLogin.setText("");
        txtSenha.setText("");
        txtPerfil.setText("");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaUsuario().setVisible(true));
    }
}