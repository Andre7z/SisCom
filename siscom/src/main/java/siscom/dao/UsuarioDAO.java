package siscom.dao;

import siscom.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UsuarioDAO {

    public boolean salvar(Usuario usuario){
        Transaction transaction = null;

        try (Session session = Conexao.getSessionFactory().openSession() ){
            transaction = session.beginTransaction();
            session.persist(usuario);
            transaction.commit();
            return true;
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            return false;
        }
    }

    public boolean alterar(Usuario usuario){
        Transaction transaction = null;

        try(Session session = Conexao.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(usuario);
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
            Usuario usuario = session.get(Usuario.class, id);

            if (usuario == null) {
                transaction.rollback();
                return false;
            }

            session.remove(usuario);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    public List<Usuario> pesquisar() {
        try (Session session = Conexao.getSessionFactory().openSession()) {
            return session.createQuery("from Usuario", Usuario.class).list();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public Usuario pesquisarPorId(int id) {
        try (Session session = Conexao.getSessionFactory().openSession()) {
            return session.get(Usuario.class, id);
        } catch (Exception e) {
            return null;
        }
    }
}