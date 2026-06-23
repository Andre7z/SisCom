package siscom.controller;

import java.util.List;

import siscom.dao.FornecedorProdutoDAO;
import siscom.model.FornecedorProduto;

public class FornecedorProdutoController {

    FornecedorProdutoDAO fornecedorProdutoDAO = new FornecedorProdutoDAO();

    public boolean salvar(FornecedorProduto fornecedorProduto) {
        return fornecedorProdutoDAO.salvar(fornecedorProduto);
    }

    public boolean alterar(FornecedorProduto fornecedorProduto) {
        return fornecedorProdutoDAO.alterar(fornecedorProduto);
    }

    public boolean excluir(int id) {
        return fornecedorProdutoDAO.excluir(id);
    }

    public FornecedorProduto pesquisar(int id) {
        return fornecedorProdutoDAO.pesquisarPorId(id);
    }

    public List<FornecedorProduto> pesquisarTodos() {
        return fornecedorProdutoDAO.pesquisar();
    }
}