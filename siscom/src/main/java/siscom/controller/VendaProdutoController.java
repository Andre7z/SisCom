package siscom.controller;

import siscom.model.VendaProduto;
import siscom.dao.VendaProdutoDAO;

public class VendaProdutoController {
    VendaProdutoDAO vendaProdutoDAO = new VendaProdutoDAO();

    public boolean salvar(VendaProduto vp) {
        return vendaProdutoDAO.salvar(vp);
    }

    public boolean alterar(VendaProduto vp) {
        return vendaProdutoDAO.alterar(vp);
    }

    public boolean excluir(int id) {
        return vendaProdutoDAO.excluir(id);
    }

}