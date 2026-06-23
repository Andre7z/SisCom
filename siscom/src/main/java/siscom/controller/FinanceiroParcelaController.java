package siscom.controller;

import java.util.List;

import siscom.dao.FinanceiroParcelaDAO;
import siscom.model.FinanceiroParcela;

public class FinanceiroParcelaController {

    FinanceiroParcelaDAO financeiroParcelaDAO = new FinanceiroParcelaDAO();

    public boolean salvar(FinanceiroParcela financeiroParcela) {
        return financeiroParcelaDAO.salvar(financeiroParcela);
    }

    public boolean alterar(FinanceiroParcela financeiroParcela) {
        return financeiroParcelaDAO.alterar(financeiroParcela);
    }

    public boolean excluir(int id) {
        return financeiroParcelaDAO.excluir(id);
    }

    public FinanceiroParcela pesquisar(int id) {
        return financeiroParcelaDAO.pesquisarPorId(id);
    }

    public List<FinanceiroParcela> pesquisarTodos() {
        return financeiroParcelaDAO.pesquisar();
    }
}