package siscom.controller;

import java.util.List;

import siscom.model.Compra;
import siscom.model.CompraProduto;
import siscom.model.Produto;
import siscom.dao.CompraDAO;
import siscom.dao.ProdutoDAO;

public class CompraController {

    CompraDAO compraDAO = new CompraDAO();
    ProdutoDAO produtoDAO = new ProdutoDAO();

    public boolean salvar(Compra compra) {

        // verifica se tem produtos
        if (compra.getProdutos() == null || compra.getProdutos().isEmpty()) {
            return false;
        }

        // calcula valor total da compra
        double total = 0;
        for (CompraProduto cp : compra.getProdutos()) { //Percorre todos os itens da compra
            total += cp.getQuantidade() * cp.getPreco_unit();
        }
        compra.setValor_total(total);

        // salva compra (e itens dentro do DAO)
        if (!compraDAO.salvar(compra)) {
            return false; //caso errado return false
        }

        // atualiza produtos (estoque + preço médio)
        for (CompraProduto cp : compra.getProdutos()) { //percorre cada produto da compra

            Produto produto = produtoDAO.pesquisar(cp.getProduto().getId()); //busca o produto no banco

            if (produto == null) {
                return false;
            }

            double estoqueAtual = produto.getQtde_estoque();
            double precoMedioAtual = produto.getPreco_medio();

            double qtdCompra = cp.getQuantidade();
            double precoCompra = cp.getPreco_unit();

            // atualiza estoque
            double novoEstoque = estoqueAtual + qtdCompra;

            // novo preço médio
            double novoPrecoMedio = 0;
            if (novoEstoque > 0) {
                novoPrecoMedio =
                        ((estoqueAtual * precoMedioAtual) + (qtdCompra * precoCompra))
                        / novoEstoque;
            }

            //atualiza no bd
            produto.setQtde_estoque(novoEstoque);
            produto.setValor_ultima_compra(precoCompra); 
            produto.setPreco_medio(novoPrecoMedio); 
            produtoDAO.alterar(produto);
        }

        return true;
    }

    public boolean alterar(Compra compra) {
        return compraDAO.alterar(compra);
    }

    public boolean excluir(int id) {
        return compraDAO.excluir(id);
    }

    public List<Compra> pesquisarTodos() {
        return compraDAO.pesquisarTodos();
    }
}