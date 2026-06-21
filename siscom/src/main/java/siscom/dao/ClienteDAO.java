package siscom.dao;

import siscom.model.Cliente;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ClienteDAO {
    Connection conn = null;

    public boolean salvar(Cliente cliente){
        Transaction transaction = null;

        try (Session session = Conexao.getSessionFactory().openSession() ){
            transaction = session.beginTransaction();
            session.persist(cliente);
            transaction.commit();
            return true;
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            return false;
        }
    }

    public boolean alterar(Cliente cliente){
        Transaction transaction = null;

        try(Session session = Conexao.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(cliente);
            transaction.commit();
            return true;
        } catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            return false;
        }
    }

    public boolean excluir(int id){
        Transaction transaction = null;

        try (Session session = Conexao.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Cliente cliente = session.get(Cliente.class, id);

            if (cliente == null) {
                transaction.rollback();
                return false;
            }

            session.remove(cliente);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    public List<Cliente> pesquisar() {
        try (Session session = Conexao.getSessionFactory().openSession()) {
            return session.createQuery("from Cliente", Cliente.class).list();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public Cliente pesquisarPorId(int id) {
        try (Session session = Conexao.getSessionFactory().openSession()) {
            return session.get(Cliente.class, id);
        } catch (Exception e) {
            return null;
        }
    }
}
// public boolean salvar(Cliente cliente) {
//     try {
//         conn = Conexao.getConnection();

//         String sql = "INSERT INTO cliente (nome, cpf, rg, endereco, telefone) VALUES (?, ?, ?, ?, ?)";
//         PreparedStatement ps = conn.prepareStatement(sql);

//         ps.setString(1, cliente.getNome());
//         ps.setString(2, cliente.getCpf());
//         ps.setString(3, cliente.getRg());
//         ps.setString(4, cliente.getEndereco());
//         ps.setString(5, cliente.getTelefone());

//         int qtdeLinhas = ps.executeUpdate();
//         ps.close();
//         return qtdeLinhas > 0;
//     } catch (Exception e) {
//         e.printStackTrace();
//         return false;
//     } finally {
//         Conexao.fecharConexao();
//     }
// }

// public boolean excluir(int id) {
//     try {
//         conn = Conexao.getConnection();

//         String sql = "DELETE FROM cliente WHERE id = ?";
//         PreparedStatement ps = conn.prepareStatement(sql);

//         ps.setInt(1, id);

//         int qtdeLinhas = ps.executeUpdate();
//         ps.close();
//         return qtdeLinhas > 0;

//     } catch (Exception e) {
//         e.printStackTrace();
//         return false;
//     } finally {
//         Conexao.fecharConexao();
//     }
// }

// public boolean alterar(Cliente cliente) {
//     try {
//         conn = Conexao.getConnection();

//         String sql = "UPDATE cliente SET nome=?, cpf=?, rg=?, endereco=?, telefone=? WHERE id=?";
//         PreparedStatement ps = conn.prepareStatement(sql);

//         ps.setString(1, cliente.getNome());
//         ps.setString(2, cliente.getCpf());
//         ps.setString(3, cliente.getRg());
//         ps.setString(4, cliente.getEndereco());
//         ps.setString(5, cliente.getTelefone());
//         ps.setInt(6, cliente.getId());

//         int qtdeLinhas = ps.executeUpdate();
//         ps.close();

//         return qtdeLinhas > 0;
//     } catch (Exception e) {
//         e.printStackTrace();
//         return false;
//     } finally {
//         Conexao.fecharConexao();
//     }
// }

// public List<Cliente> pesquisarTodos() {

//     try {
//         List<Cliente> clientes = new ArrayList<>();
//         conn = Conexao.getConnection();

//         String sql = "SELECT * FROM cliente WHERE id=?";
//         PreparedStatement ps = conn.prepareStatement(sql);

//         ResultSet rs = ps.executeQuery();

//         while (rs.next()) {
//             Cliente cliente = new Cliente();

//             cliente = new Cliente();
//             cliente.setId(rs.getInt("id"));
//             cliente.setNome(rs.getString("nome"));
//             cliente.setCpf(rs.getString("cpf"));
//             cliente.setRg(rs.getString("rg"));
//             cliente.setEndereco(rs.getString("endereco"));
//             cliente.setTelefone(rs.getString("telefone"));

//             clientes.add(cliente);
//         }
//         rs.close();
//         ps.close();
//         return clientes;

//     } catch (Exception e) {
//         e.printStackTrace();
//         return null;
//     } finally {
//         Conexao.fecharConexao();
//     }
// }