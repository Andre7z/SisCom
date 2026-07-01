package siscom.dao;

import siscom.model.FinanceiroParcela;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class FinanceiroParcelaDAO {

    public boolean salvar(FinanceiroParcela financeiroParcela){
        Transaction transaction = null;

        try (Session session = Conexao.getSessionFactory().openSession() ){
            transaction = session.beginTransaction();
            session.persist(financeiroParcela);
            transaction.commit();
            return true;
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            return false;
        }
    }

    public boolean alterar(FinanceiroParcela financeiroParcela){
        Transaction transaction = null;

        try(Session session = Conexao.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(financeiroParcela);
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
            FinanceiroParcela financeiroParcela = session.get(FinanceiroParcela.class, id);

            if (financeiroParcela == null) {
                transaction.rollback();
                return false;
            }

            session.remove(financeiroParcela);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    public List<FinanceiroParcela> pesquisar() {
        try (Session session = Conexao.getSessionFactory().openSession()) {
            return session.createQuery("from FinanceiroParcela", FinanceiroParcela.class).list();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public FinanceiroParcela pesquisarPorId(int id) {
        try (Session session = Conexao.getSessionFactory().openSession()) {
            return session.get(FinanceiroParcela.class, id);
        } catch (Exception e) {
            return null;
        }
    }
}