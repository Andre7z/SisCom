package siscom.view;

import java.awt.*;
import javax.swing.*;

import siscom.controller.UsuarioController;
import siscom.model.Usuario;

public class TelaLogin extends JFrame {

    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnEntrar;

    private UsuarioController controller;

    public TelaLogin() {
        controller = new UsuarioController();

        setTitle("Login - SisCom");
        setSize(600, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel(
                "Sistema SisCom - Login",
                SwingConstants.CENTER
        );

        JPanel painelCampos = new JPanel(new GridLayout(2, 2, 5, 5));

        painelCampos.add(new JLabel("Login:"));
        txtLogin = new JTextField();
        painelCampos.add(txtLogin);

        painelCampos.add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        painelCampos.add(txtSenha);

        btnEntrar = new JButton("Entrar");
        btnEntrar.addActionListener(e -> autenticar());

        JPanel painelBotao = new JPanel();
        painelBotao.add(btnEntrar);

        add(titulo, BorderLayout.NORTH);
        add(painelCampos, BorderLayout.CENTER);
        add(painelBotao, BorderLayout.SOUTH);
    }

    private void autenticar() {
        String login = txtLogin.getText();
        String senha = new String(txtSenha.getPassword());

        Usuario usuario = controller.autenticar(login, senha);

        if (usuario != null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Login realizado com sucesso!"
            );

            dispose();
            new TelaPrincipal().setVisible(true);

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Login ou senha inválidos",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new TelaLogin().setVisible(true)
        );
    }
}