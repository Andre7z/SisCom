package siscom.dao;

import siscom.model.VendaProduto;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class VendaProdutoDAO {

    public boolean salvar(VendaProduto vendaProduto){
        Transaction transaction = null;

        try (Session session = Conexao.getSessionFactory().openSession() ){
            transaction = session.beginTransaction();
            session.persist(vendaProduto);
            transaction.commit();
            return true;
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            return false;
        }
    }

    public boolean alterar(VendaProduto vendaProduto){
        Transaction transaction = null;

        try(Session session = Conexao.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(vendaProduto);
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
            VendaProduto vendaProduto = session.get(VendaProduto.class, id);

            if (vendaProduto == null) {
                transaction.rollback();
                return false;
            }

            session.remove(vendaProduto);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    public List<VendaProduto> pesquisar() {
        try (Session session = Conexao.getSessionFactory().openSession()) {
            return session.createQuery("from VendaProduto", VendaProduto.class).list();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public VendaProduto pesquisarPorId(int id) {
        try (Session session = Conexao.getSessionFactory().openSession()) {
            return session.get(VendaProduto.class, id);
        } catch (Exception e) {
            return null;
        }
    }
}
    
    // public boolean salvar(VendaProduto produto) {
    //     try {
    //         conn = Conexao.getConnection();
    
    //         String sql = "INSERT INTO venda_produto (venda_id, produto_id, quantidade, preco_unit) VALUES (?, ?, ?, ?)";
    //         PreparedStatement ps = conn.prepareStatement(sql);
    
    //         ps.setInt(1, produto.getVenda().getId());
    //         ps.setInt(2, produto.getProduto().getId());
    //         ps.setInt(3, produto.getQuantidade());
    //         ps.setDouble(4, produto.getPreco_unit());
    
    //         ps.executeUpdate();
    //         ps.close();
    
    //         System.out.println("produtoVenda salvo!");
    //         return true;
    
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
    
    //         String sql = "DELETE FROM venda_produto WHERE id=?";
    //         PreparedStatement ps = conn.prepareStatement(sql);
    
    //         ps.setInt(1, id);
    
    //         ps.executeUpdate();
    //         ps.close();
    
    //         System.out.println("produtoVenda excluído!");
    //         return true;
    
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return false;
    //     } finally {
    //         Conexao.fecharConexao();
    //     }
    // }
    
    
    // public boolean alterar(VendaProduto produto) {
    //     try {
    //         conn = Conexao.getConnection();
    
    //         String sql = "UPDATE venda_produto SET quantidade=?, preco_unit=? WHERE id=?";
    //         PreparedStatement ps = conn.prepareStatement(sql);
    
    //         ps.setInt(1, produto.getQuantidade());
    //         ps.setDouble(2, produto.getPreco_unit());
    //         ps.setInt(3, produto.getId());
    
    //         ps.executeUpdate();
    //         ps.close();
    
    //         System.out.println("produtoVenda alterado!");
    //         return true;
    
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return false;
    //     } finally {
    //         Conexao.fecharConexao();
    //     }
    // }
    
    // public VendaProduto pesquisar(int id) {
    //     VendaProduto produto = null;
    
    //     try {
    //         conn = Conexao.getConnection();
    
    //         String sql = "SELECT * FROM venda_produto WHERE id=?";
    //         PreparedStatement ps = conn.prepareStatement(sql);
    
    //         ps.setInt(1, id);
    
    //         ResultSet rs = ps.executeQuery();
    
    //         if (rs.next()) {
    //             produto = new VendaProduto();
    
    //             produto.setId(rs.getInt("id"));
    //             produto.setQuantidade(rs.getInt("quantidade"));
    //             produto.setPreco_unit(rs.getDouble("preco_unit"));
    
    //             Produto p = new Produto();
    //             p.setId(rs.getInt("produto_id"));
    
    //             Venda v = new Venda();
    //             v.setId(rs.getInt("venda_id"));
    
    //             produto.setProduto(p);
    //             produto.setVenda(v);
    //         }
    
    //         rs.close();
    //         ps.close();
    
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     } finally {
    //         Conexao.fecharConexao();
    //     }
    
    //     return produto;
    // }