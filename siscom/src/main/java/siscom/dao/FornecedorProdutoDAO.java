package siscom.dao;

import siscom.model.FornecedorProduto;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class FornecedorProdutoDAO {

    public boolean salvar(FornecedorProduto fornecedorProduto){
        Transaction transaction = null;

        try (Session session = Conexao.getSessionFactory().openSession() ){
            transaction = session.beginTransaction();
            session.persist(fornecedorProduto);
            transaction.commit();
            return true;
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            return false;
        }
    }

    public boolean alterar(FornecedorProduto fornecedorProduto){
        Transaction transaction = null;

        try(Session session = Conexao.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(fornecedorProduto);
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
            FornecedorProduto fornecedorProduto = session.get(FornecedorProduto.class, id);

            if (fornecedorProduto == null) {
                transaction.rollback();
                return false;
            }

            session.remove(fornecedorProduto);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    public List<FornecedorProduto> pesquisar() {
        try (Session session = Conexao.getSessionFactory().openSession()) {
            return session.createQuery("from FornecedorProduto", FornecedorProduto.class).list();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public FornecedorProduto pesquisarPorId(int id) {
        try (Session session = Conexao.getSessionFactory().openSession()) {
            return session.get(FornecedorProduto.class, id);
        } catch (Exception e) {
            return null;
        }
    }
}
// public boolean salvar(FornecedorProduto fp) {
//     try {
//         conn = Conexao.getConnection();

//         String sql = "INSERT INTO fornecedor_produto (fornecedor_id, produto_id) VALUES (?, ?)";

//         PreparedStatement ps = conn.prepareStatement(sql);

//         ps.setInt(1, fp.getFornecedor().getId());
//         ps.setInt(2, fp.getProduto().getId());

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

// public boolean excluir(int id){
//     try{
//         conn = Conexao.getConnection();

//         String sql = "DELETE FROM fornecedor_produto WHERE ID = ?";
//         PreparedStatement ps = conn.prepareStatement(sql);

//         ps.setInt(1, id);

//         int qtdeLinhas = ps.executeUpdate();
//         ps.close();
//         return qtdeLinhas > 0;
//     } catch (Exception e){
//         e.printStackTrace();
//         return false;}
//         finally {
//             Conexao.fecharConexao();
//         }
//     }
//         public boolean alterar(FornecedorProduto fn) {
//     try {
//         conn = Conexao.getConnection();

//         String sql = "UPDATE fornecedor_produto SET nome=? WHERE ID = ?";
//         PreparedStatement ps = conn.prepareStatement(sql);

//         ps.setInt(1, fn.getFornecedor().getId());
//         ps.setInt(2,fn.getProduto().getId());

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

// public List<FornecedorProduto> pesquisarTodos() {
//     try {
//         List<FornecedorProduto> FornecedorProdutos = new ArrayList<>();

//         conn = Conexao.getConnection();

//         String sql = "SELECT * FROM fornecedor_produto";
//         PreparedStatement ps = conn.prepareStatement(sql);

//         ResultSet rs = ps.executeQuery();

//         while (rs.next()) {
//             FornecedorProduto fn = new FornecedorProduto();

//             ps.setInt(1, fn.getFornecedor().getId());
//             ps.setInt(2,fn.getProduto().getId());

//             FornecedorProdutos.add(fn);
//         }

//         rs.close();
//         ps.close();
//         return FornecedorProdutos;

//     } catch (Exception e) {
//         e.printStackTrace();
//         return null;
//     } finally {
//         Conexao.fecharConexao();
//     }
// }