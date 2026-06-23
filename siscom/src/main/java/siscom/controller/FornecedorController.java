package siscom.controller;

import java.util.List;

import siscom.dao.FornecedorDAO;
import siscom.model.Fornecedor;

public class FornecedorController {

    FornecedorDAO fornecedorDAO = new FornecedorDAO();

    public boolean salvar(Fornecedor fornecedor) {
        return fornecedorDAO.salvar(fornecedor);
    }

    public boolean alterar(Fornecedor fornecedor) {
        return fornecedorDAO.alterar(fornecedor);
    }

    public boolean excluir(int id) {
        return fornecedorDAO.excluir(id);
    }

    public Fornecedor pesquisar(int id) {
        return fornecedorDAO.pesquisarPorId(id);
    }

    public List<Fornecedor> pesquisarTodos() {
        return fornecedorDAO.pesquisar();
    }
}