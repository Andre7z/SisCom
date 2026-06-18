package siscom.controller;

import siscom.model.CompraProduto;
import siscom.dao.CompraProdutoDAO;

public class CompraProdutoController {

    CompraProdutoDAO cpDAO = new CompraProdutoDAO();

    public boolean salvar(CompraProduto cp) {
        return cpDAO.salvar(cp);
    }

    public boolean alterar(CompraProduto cp) {
        return cpDAO.alterar(cp);
    }

    public boolean excluir(int id) {
        return cpDAO.excluir(id);
    }

}