package siscom.controller;

import java.util.List;

import siscom.dao.VendaProdutoDAO;
import siscom.model.VendaProduto;

public class VendaProdutoController {

    VendaProdutoDAO vendaProdutoDAO = new VendaProdutoDAO();

    public boolean salvar(VendaProduto vendaProduto) {
        return vendaProdutoDAO.salvar(vendaProduto);
    }

    public boolean alterar(VendaProduto vendaProduto) {
        return vendaProdutoDAO.alterar(vendaProduto);
    }

    public boolean excluir(int id) {
        return vendaProdutoDAO.excluir(id);
    }

    public VendaProduto pesquisar(int id) {
        return vendaProdutoDAO.pesquisarPorId(id);
    }

    public List<VendaProduto> pesquisarTodos() {
        return vendaProdutoDAO.pesquisar();
    }
}