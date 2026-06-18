package siscom.controller;

import siscom.dao.FornecedorProdutoDAO;
import siscom.model.FornecedorProduto;

public class FornecedorProdutoController {

    FornecedorProdutoDAO fornecedorProdutoDAO = new FornecedorProdutoDAO();

    public boolean salvar(FornecedorProduto fp) {
        return fornecedorProdutoDAO.salvar(fp);
    }

    public boolean alterar(FornecedorProduto fp) {
        return fornecedorProdutoDAO.alterar(fp);
    }

    public boolean excluir(int id) {
        return fornecedorProdutoDAO.excluir(id);
    }

}