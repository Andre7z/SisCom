package siscom.dao;

import siscom.model.Categoria;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CategoriaDAO {

    public boolean salvar(Categoria categoria){
        Transaction transaction = null;

        try (Session session = Conexao.getSessionFactory().openSession() ){
            transaction = session.beginTransaction();
            session.persist(categoria);
            transaction.commit();
            return true;
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            return false;
        }
    }

    public boolean alterar(Categoria categoria){
        Transaction transaction = null;

        try(Session session = Conexao.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(categoria);
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
            Categoria categoria = session.get(Categoria.class, id);

            if (categoria == null) {
                transaction.rollback();
                return false;
            }

            session.remove(categoria);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    public List<Categoria> pesquisar() {
        try (Session session = Conexao.getSessionFactory().openSession()) {
            return session.createQuery("from Categoria", Categoria.class).list();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public Categoria pesquisarPorId(int id) {
        try (Session session = Conexao.getSessionFactory().openSession()) {
            return session.get(Categoria.class, id);
        } catch (Exception e) {
            return null;
        }
    }
}

// public boolean salvar(Categoria categoria) {
//     try {
//         conn = Conexao.getConnection();

//         String sql = "INSERT INTO CATEGORIA (nome) VALUES (?)";
//         PreparedStatement ps = conn.prepareStatement(sql);

//         ps.setString(1, categoria.getNome());

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

//         String sql = "DELETE FROM categoria WHERE ID = ? ";
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

// public boolean alterar(Categoria categoria) {
//     try {
//         conn = Conexao.getConnection();

//         String sql = "UPDATE categoria SET nome=? WHERE ID = ?";
//         PreparedStatement ps = conn.prepareStatement(sql);

//         ps.setString(1, categoria.getNome());
//         ps.setInt(2,categoria.getId());

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

// public List<Categoria> pesquisarTodos() {
//     try {
//         List<Categoria> categorias = new ArrayList<>();

//         conn = Conexao.getConnection();

//         String sql = "SELECT * FROM categoria";
//         PreparedStatement ps = conn.prepareStatement(sql);

//         ResultSet rs = ps.executeQuery();

//         while (rs.next()) {
//             Categoria categoria = new Categoria();

//             categoria.setId(rs.getInt("id"));
//             categoria.setNome(rs.getString("nome"));

//             categorias.add(categoria);
//         }

//         rs.close();
//         ps.close();
//         return categorias;

//     } catch (Exception e) {
//         e.printStackTrace();
//         return null;
//     } finally {
//         Conexao.fecharConexao();
//     }
// }