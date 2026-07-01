package siscom.dao;

import java.io.InputStream;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import siscom.model.Categoria;
import siscom.model.Cliente;
import siscom.model.Compra;
import siscom.model.CompraProduto;
import siscom.model.Financeiro;
import siscom.model.FinanceiroParcela;
import siscom.model.FormaPagamento;
import siscom.model.Fornecedor;
import siscom.model.FornecedorProduto;
import siscom.model.Produto;
import siscom.model.TipoConta;
import siscom.model.Usuario;
import siscom.model.Venda;
import siscom.model.VendaProduto;

public class Conexao {

    private static final SessionFactory SESSION_FACTORY = criarSessao();

    private static SessionFactory criarSessao() {
        try {
            Properties propriedades = new Properties();

            InputStream inputStream =
                    Conexao.class.getClassLoader()
                            .getResourceAsStream("hibernate.properties");

            if (inputStream == null) {
                throw new RuntimeException(
                        "Arquivo hibernate.properties nao encontrado."
                );
            }

            propriedades.load(inputStream);

            Configuration configuration = new Configuration();
            configuration.setProperties(propriedades);

            configuration.addAnnotatedClass(Categoria.class);
            configuration.addAnnotatedClass(Cliente.class);
            configuration.addAnnotatedClass(Fornecedor.class);
            configuration.addAnnotatedClass(FornecedorProduto.class);
            configuration.addAnnotatedClass(Produto.class);

            configuration.addAnnotatedClass(Venda.class);
            configuration.addAnnotatedClass(VendaProduto.class);

            configuration.addAnnotatedClass(Compra.class);
            configuration.addAnnotatedClass(CompraProduto.class);

            configuration.addAnnotatedClass(Usuario.class);
            configuration.addAnnotatedClass(FormaPagamento.class);
            configuration.addAnnotatedClass(TipoConta.class);
            configuration.addAnnotatedClass(Financeiro.class);
            configuration.addAnnotatedClass(FinanceiroParcela.class);

            return configuration.buildSessionFactory();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao configurar Hibernate", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}