package siscom.controller;

import java.util.List;

import siscom.dao.CompraProdutoDAO;
import siscom.model.CompraProduto;

public class CompraProdutoController {

    CompraProdutoDAO compraProdutoDAO = new CompraProdutoDAO();

    public boolean salvar(CompraProduto compraProduto) {
        return compraProdutoDAO.salvar(compraProduto);
    }

    public boolean alterar(CompraProduto compraProduto) {
        return compraProdutoDAO.alterar(compraProduto);
    }

    public boolean excluir(int id) {
        return compraProdutoDAO.excluir(id);
    }

    public CompraProduto pesquisar(int id) {
        return compraProdutoDAO.pesquisarPorId(id);
    }

    public List<CompraProduto> pesquisarTodos() {
        return compraProdutoDAO.pesquisar();
    }
}