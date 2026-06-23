package siscom.controller;

import java.util.List;

import siscom.dao.ProdutoDAO;
import siscom.model.Produto;

public class ProdutoController {

    ProdutoDAO produtoDAO = new ProdutoDAO();

    public boolean salvar(Produto produto) {
        return produtoDAO.salvar(produto);
    }

    public boolean alterar(Produto produto) {
        return produtoDAO.alterar(produto);
    }

    public boolean excluir(int id) {
        return produtoDAO.excluir(id);
    }

    public Produto pesquisar(int id) {
        return produtoDAO.pesquisarPorId(id);
    }

    public List<Produto> pesquisarTodos() {
        return produtoDAO.pesquisar();
    }

    public boolean verificaEstoqueExistente(Produto produto) {
        Produto produtoExistente = produtoDAO.pesquisarPorId(produto.getId());

        if (produtoExistente == null) {
            return false;
        }

        return produtoExistente.getQuantidade() >= 1;
    }

    public boolean atualizarEstoqueVenda(Produto produto, int quantidadeVendida) {
        Produto produtoExistente = produtoDAO.pesquisarPorId(produto.getId());

        if (produtoExistente == null) {
            return false;
        }

        if (produtoExistente.getQuantidade() < quantidadeVendida) {
            return false;
        }

        produtoExistente.setQuantidade(
            produtoExistente.getQuantidade() - quantidadeVendida
        );

        return produtoDAO.alterar(produtoExistente);
    }

    public boolean atualizarEstoqueCompra(Produto produto, int quantidadeComprada) {
        Produto produtoExistente = produtoDAO.pesquisarPorId(produto.getId());

        if (produtoExistente == null) {
            return false;
        }

        produtoExistente.setQuantidade(
            produtoExistente.getQuantidade() + quantidadeComprada
        );

        return produtoDAO.alterar(produtoExistente);
    }

    public boolean atualizarUltimaVenda(Produto produto, Double valorVenda) {
        Produto produtoExistente = produtoDAO.pesquisarPorId(produto.getId());

        if (produtoExistente == null) {
            return false;
        }

        produtoExistente.setValorUltimaVenda(valorVenda);

        return produtoDAO.alterar(produtoExistente);
    }

    public boolean atualizarUltimaCompra(Produto produto, Double valorCompra) {
        Produto produtoExistente = produtoDAO.pesquisarPorId(produto.getId());

        if (produtoExistente == null) {
            return false;
        }

        produtoExistente.setValorUltimaCompra(valorCompra);

        return produtoDAO.alterar(produtoExistente);
    }

    public boolean atualizarPrecoMedio(Produto produto, Double precoMedio) {
        Produto produtoExistente = produtoDAO.pesquisarPorId(produto.getId());

        if (produtoExistente == null) {
            return false;
        }

        produtoExistente.setPrecoMedio(precoMedio);

        return produtoDAO.alterar(produtoExistente);
    }
}