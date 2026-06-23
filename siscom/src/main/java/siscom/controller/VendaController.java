package siscom.controller;

import java.util.List;

import siscom.dao.VendaDAO;
import siscom.model.Venda;
import siscom.model.VendaProduto;

public class VendaController {

    VendaDAO vendaDAO = new VendaDAO();
    ProdutoController produtoController = new ProdutoController();

    public boolean salvar(Venda venda) {
        if (venda == null) {
            return false;
        }

        for (VendaProduto item : venda.getVendaProdutos()) {
            if (!produtoController.verificaEstoqueExistente(item.getProduto())) {
                return false;
            }

            if (!produtoController.atualizarEstoqueVenda(
                    item.getProduto(),
                    item.getQuantidade())) {
                return false;
            }

            produtoController.atualizarUltimaVenda(
                    item.getProduto(),
                    item.getValorUnitario());
        }

        return vendaDAO.salvar(venda);
    }

    public boolean alterar(Venda venda) {
        return vendaDAO.alterar(venda);
    }

    public boolean excluir(int id) {
        return vendaDAO.excluir(id);
    }

    public Venda pesquisar(int id) {
        return vendaDAO.pesquisarPorId(id);
    }

    public List<Venda> pesquisarTodos() {
        return vendaDAO.pesquisar();
    }
}