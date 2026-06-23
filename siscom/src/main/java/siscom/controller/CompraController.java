package siscom.controller;

import java.util.List;

import siscom.dao.CompraDAO;
import siscom.model.Compra;
import siscom.model.CompraProduto;

public class CompraController {

    CompraDAO compraDAO = new CompraDAO();
    ProdutoController produtoController = new ProdutoController();

    public boolean salvar(Compra compra) {
        if (compra == null) {
            return false;
        }

        for (CompraProduto item : compra.getProdutos()) {

            if (!produtoController.atualizarEstoqueCompra(
                    item.getProduto(),
                    item.getQuantidade())) {
                return false;
            }

            produtoController.atualizarUltimaCompra(
                    item.getProduto(),
                    item.getValorUnitario());

            produtoController.atualizarPrecoMedio(
                    item.getProduto(),
                    item.getValorUnitario());
        }

        return compraDAO.salvar(compra);
    }

    public boolean alterar(Compra compra) {
        return compraDAO.alterar(compra);
    }

    public boolean excluir(int id) {
        return compraDAO.excluir(id);
    }

    public Compra pesquisar(int id) {
        return compraDAO.pesquisarPorId(id);
    }

    public List<Compra> pesquisarTodos() {
        return compraDAO.pesquisar();
    }
}