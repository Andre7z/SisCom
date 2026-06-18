package siscom.controller;

import java.util.List;

import siscom.dao.VendaDAO;
import siscom.dao.ProdutoDAO;
import siscom.model.Venda;
import siscom.model.Produto;
import siscom.model.VendaProduto;

public class VendaController {

    VendaDAO vendaDAO = new VendaDAO();
    ProdutoDAO produtoDAO = new ProdutoDAO();

    public boolean alterar(Venda venda) {
        return vendaDAO.alterar(venda);
    }

    public boolean excluir(int id) {
        return vendaDAO.excluir(id);
    }

    public List<Venda> pesquisarTodos() {
        return vendaDAO.pesquisarTodos();
    }

    public boolean salvar(Venda venda) {

        // calcula valor total
        double total = 0;
        for (VendaProduto vp : venda.getprodutos()) { //percorre todos os produtos da venda
            total += vp.getQuantidade() * vp.getPreco_unit();
        }
        venda.setValor_total(total);

        // verifica se o cliente já comprou 3 vezes
        int totalVendas = vendaDAO.restrigirVendas(venda.getCliente().getCpf());
        if (totalVendas >= 3) {
            return false;
        }

        // verifica estoque
        for (VendaProduto vp : venda.getprodutos()) { //percorre todos os itens da venda

            Produto produto = produtoDAO.pesquisar(vp.getProduto().getId()); //busca o produto no banco

            if (produto == null) return false;

            if (produto.getQtde_estoque() < vp.getQuantidade()) { //Verifica se tem a quantidade o cliente quer
                return false;
            }
        }

        // atualiza estoque e ultima venda
        for (VendaProduto vp : venda.getprodutos()) { //percorre todos os itens da venda

            Produto produto = produtoDAO.pesquisar(vp.getProduto().getId()); //busca o produto no banco

            produto.setQtde_estoque(produto.getQtde_estoque() - vp.getQuantidade()); //diminui o estoque
            produto.setValor_ultima_venda(vp.getPreco_unit()); //guarda o último preço que foi vendido

            produtoDAO.alterar(produto); //atualiza produto
        }

        // salva venda
        return vendaDAO.salvar(venda);
    }
}