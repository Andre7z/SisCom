package siscom.dao;

import siscom.model.TipoConta;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TipoContaDAO {
    Connection conn = null;

    public boolean salvar(TipoConta tipoConta){
        Transaction transaction = null;

        try (Session session = Conexao.getSessionFactory().openSession() ){
            transaction = session.beginTransaction();
            session.persist(tipoConta);
            transaction.commit();
            return true;
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            return false;
        }
    }

    public boolean alterar(TipoConta tipoConta){
        Transaction transaction = null;

        try(Session session = Conexao.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(tipoConta);
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
            TipoConta tipoConta = session.get(TipoConta.class, id);

            if (tipoConta == null) {
                transaction.rollback();
                return false;
            }

            session.remove(tipoConta);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    public List<TipoConta> pesquisar() {
        try (Session session = Conexao.getSessionFactory().openSession()) {
            return session.createQuery("from TipoConta", TipoConta.class).list();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public TipoConta pesquisarPorId(int id) {
        try (Session session = Conexao.getSessionFactory().openSession()) {
            return session.get(TipoConta.class, id);
        } catch (Exception e) {
            return null;
        }
    }
}