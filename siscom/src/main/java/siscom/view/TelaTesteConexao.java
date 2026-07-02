package siscom.view;

import siscom.dao.Conexao;

import javax.swing.*;
import java.awt.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class TelaTesteConexao extends JFrame {

    private JButton btnTestar;
    private JLabel lblStatus;

    public TelaTesteConexao() {
        setTitle("Teste de Conexão");
        setSize(400, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        iniciarComponentes();
    }

    private void iniciarComponentes() {

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(2, 1, 10, 10));
        painel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        btnTestar = new JButton("Testar Conexão Hibernate");
        lblStatus = new JLabel(
                "Status: aguardando teste...",
                SwingConstants.CENTER
        );

        btnTestar.addActionListener(e -> testarConexao());

        painel.add(btnTestar);
        painel.add(lblStatus);

        add(painel);
    }

    private void testarConexao() {

        try {
            SessionFactory factory = Conexao.getSessionFactory();
            Session session = factory.openSession();

            if (session != null && session.isOpen()) {

                lblStatus.setText(
                        "Status: conexão realizada com sucesso!"
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Conectado ao PostgreSQL com Hibernate!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );

                session.close();

            } else {

                lblStatus.setText("Status: falha na conexão.");

                JOptionPane.showMessageDialog(
                        this,
                        "Não foi possível conectar.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception ex) {

            lblStatus.setText("Status: erro ao conectar.");

            JOptionPane.showMessageDialog(
                    this,
                    "Erro: " + ex.getMessage(),
                    "Erro de conexão",
                    JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaTesteConexao().setVisible(true);
        });
    }
}