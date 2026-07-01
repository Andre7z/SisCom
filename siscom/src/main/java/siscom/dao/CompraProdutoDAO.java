package siscom.dao;

import siscom.model.CompraProduto;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CompraProdutoDAO {

    public boolean salvar(CompraProduto compraProduto){
        Transaction transaction = null;

        try (Session session = Conexao.getSessionFactory().openSession() ){
            transaction = session.beginTransaction();
            session.persist(compraProduto);
            transaction.commit();
            return true;
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            return false;
        }
    }

    public boolean alterar(CompraProduto compraProduto){
        Transaction transaction = null;

        try(Session session = Conexao.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(compraProduto);
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
            CompraProduto compraProduto = session.get(CompraProduto.class, id);

            if (compraProduto == null) {
                transaction.rollback();
                return false;
            }

            session.remove(compraProduto);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    public List<CompraProduto> pesquisar() {
        try (Session session = Conexao.getSessionFactory().openSession()) {
            return session.createQuery("from CompraProduto", CompraProduto.class).list();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public CompraProduto pesquisarPorId(int id) {
        try (Session session = Conexao.getSessionFactory().openSession()) {
            return session.get(CompraProduto.class, id);
        } catch (Exception e) {
            return null;
        }
    }
}
// public boolean salvar(CompraProduto cp) {
//     try {
//         conn = Conexao.getConnection();

//         String sql = "INSERT INTO compra_produto (compra_id, produto_id, quantidade, preco_unit) VALUES (?, ?, ?, ?)";
//         PreparedStatement ps = conn.prepareStatement(sql);

//         ps.setInt(1, cp.getCompra().getId());
//         ps.setInt(2, cp.getProduto().getId());
//         ps.setInt(3, cp.getQuantidade());
//         ps.setDouble(4, cp.getPreco_unit());

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

//         String sql = "DELETE FROM compra_produto WHERE id=?";
//         PreparedStatement ps = conn.prepareStatement(sql);

//         ps.setInt(1, id);

//         ps.executeUpdate();
//         ps.close();

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

// public boolean alterar(CompraProduto cp) {
//     try {
//         conn = Conexao.getConnection();

//         String sql = "UPDATE compra_produto SET quantidade=?, preco_unit=? WHERE id=?";
//         PreparedStatement ps = conn.prepareStatement(sql);

//         ps.setInt(1, cp.getQuantidade());
//         ps.setDouble(2, cp.getPreco_unit());
//         ps.setInt(3, cp.getId());

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

// public List<CompraProduto> pesquisarTodos() {
//     try {
//         List<CompraProduto> cps = new ArrayList<>();

//         conn = Conexao.getConnection();

//         String sql = "SELECT * FROM compra_produto WHERE id=?";
//         PreparedStatement ps = conn.prepareStatement(sql);

//         ResultSet rs = ps.executeQuery();

//         while (rs.next()) {
//             CompraProduto cp = new CompraProduto();

//             cp.setId(rs.getInt("id"));
//             cp.setQuantidade(rs.getInt("quantidade"));
//             cp.setPreco_unit(rs.getDouble("preco_unit"));

//             Produto p = new Produto();
//             p.setId(rs.getInt("produto_id"));

//             Compra c = new Compra();
//             c.setId(rs.getInt("compra_id"));

//             cp.setProduto(p);
//             cp.setCompra(c);
//         }

//         rs.close();
//         ps.close();
//         return cps;

//     } catch (Exception e) {
//         e.printStackTrace();
//         return null;
//     } finally {
//         Conexao.fecharConexao();
//     }
// }