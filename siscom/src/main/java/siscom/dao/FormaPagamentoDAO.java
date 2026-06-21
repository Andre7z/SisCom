package siscom.dao;

import siscom.model.FormaPagamento;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class FormaPagamentoDAO {
    Connection conn = null;

    public boolean salvar(FormaPagamento formaPagamento){
        Transaction transaction = null;

        try (Session session = Conexao.getSessionFactory().openSession() ){
            transaction = session.beginTransaction();
            session.persist(formaPagamento);
            transaction.commit();
            return true;
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            return false;
        }
    }

    public boolean alterar(FormaPagamento formaPagamento){
        Transaction transaction = null;

        try(Session session = Conexao.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(formaPagamento);
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
            FormaPagamento formaPagamento = session.get(FormaPagamento.class, id);

            if (formaPagamento == null) {
                transaction.rollback();
                return false;
            }

            session.remove(formaPagamento);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    public List<FormaPagamento> pesquisar() {
        try (Session session = Conexao.getSessionFactory().openSession()) {
            return session.createQuery("from FormaPagamento", FormaPagamento.class).list();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public FormaPagamento pesquisarPorId(int id) {
        try (Session session = Conexao.getSessionFactory().openSession()) {
            return session.get(FormaPagamento.class, id);
        } catch (Exception e) {
            return null;
        }
    }
}