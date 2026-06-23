package siscom.controller;

import java.util.List;

import siscom.dao.FormaPagamentoDAO;
import siscom.model.FormaPagamento;

public class FormaPagamentoController {

    FormaPagamentoDAO formaPagamentoDAO = new FormaPagamentoDAO();

    public boolean salvar(FormaPagamento formaPagamento) {
        return formaPagamentoDAO.salvar(formaPagamento);
    }

    public boolean alterar(FormaPagamento formaPagamento) {
        return formaPagamentoDAO.alterar(formaPagamento);
    }

    public boolean excluir(int id) {
        return formaPagamentoDAO.excluir(id);
    }

    public FormaPagamento pesquisar(int id) {
        return formaPagamentoDAO.pesquisarPorId(id);
    }

    public List<FormaPagamento> pesquisarTodos() {
        return formaPagamentoDAO.pesquisar();
    }
}