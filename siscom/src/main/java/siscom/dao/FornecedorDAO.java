package siscom.dao;

import siscom.model.Fornecedor;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class FornecedorDAO {

    public boolean salvar(Fornecedor fornecedor){
        Transaction transaction = null;

        try (Session session = Conexao.getSessionFactory().openSession() ){
            transaction = session.beginTransaction();
            session.persist(fornecedor);
            transaction.commit();
            return true;
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            return false;
        }
    }

    public boolean alterar(Fornecedor fornecedor){
        Transaction transaction = null;

        try(Session session = Conexao.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(fornecedor);
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
            Fornecedor fornecedor = session.get(Fornecedor.class, id);

            if (fornecedor == null) {
                transaction.rollback();
                return false;
            }

            session.remove(fornecedor);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    public List<Fornecedor> pesquisar() {
        try (Session session = Conexao.getSessionFactory().openSession()) {
            return session.createQuery("from Fornecedor", Fornecedor.class).list();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public Fornecedor pesquisarPorId(int id) {
        try (Session session = Conexao.getSessionFactory().openSession()) {
            return session.get(Fornecedor.class, id);
        } catch (Exception e) {
            return null;
        }
    }
}

// public boolean salvar(Fornecedor fornecedor) {
//     try {
//         conn = Conexao.getConnection();

//         String sql = "INSERT INTO FORNECEDOR (nome_fantasia, razao_social, cnpj) VALUES (?,?,?)";
//         PreparedStatement ps = conn.prepareStatement(sql);

//         ps.setString(1, fornecedor.getNome_fantasia());
//         ps.setString(2, fornecedor.getRazao_social());
//         ps.setString(3, fornecedor.getCnpj());

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

//         String sql = "DELETE FROM fornecedor WHERE ID = ? ";
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


// public boolean alterar(Fornecedor fornecedor) {
//     try {
//         conn = Conexao.getConnection();

//         String sql = "UPDATE fornecedor SET nome=?";
//         PreparedStatement ps = conn.prepareStatement(sql);

//         ps.setString(1, fornecedor.getNome_fantasia());
//         ps.setString(2, fornecedor.getRazao_social());
//         ps.setString(3, fornecedor.getCnpj());

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

// public List<Fornecedor> pesquisarTodos() {
//     try {
//         List<Fornecedor> fornecedores = new ArrayList<>();

//         conn = Conexao.getConnection();

//         String sql = "SELECT * FROM fornecedor WHERE id=?";
//         PreparedStatement ps = conn.prepareStatement(sql);

//         ResultSet rs = ps.executeQuery();

//         while (rs.next()) {
//             Fornecedor fornecedor = new Fornecedor();

//             fornecedor.setId(rs.getInt("id"));
//             fornecedor.setNome_fantasia(rs.getString("nome_fantasia"));
//             fornecedor.setRazao_social(rs.getString("razao_social"));
//             fornecedor.setCnpj(rs.getString("cnpj"));

//             fornecedores.add(fornecedor);
//         }
//         rs.close();
//         ps.close();
//         return fornecedores;

//     } catch (Exception e) {
//         e.printStackTrace();
//         return null;
//     } finally {
//         Conexao.fecharConexao();
//     }
// }