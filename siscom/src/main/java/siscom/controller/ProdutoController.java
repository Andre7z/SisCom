package siscom.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import siscom.dao.ProdutoDAO;
import siscom.model.Produto;

public class ProdutoController {

    private static final Logger logger =
            LogManager.getLogger(ProdutoController.class);

    private ProdutoDAO produtoDAO = new ProdutoDAO();

    public boolean salvar(Produto produto) {
        logger.info("Iniciando salvar Produto");

        try {
            if (produto == null) {
                logger.error("Produto nulo");
                return false;
            }

            if (produto.getNome() == null || produto.getNome().isBlank()) {
                logger.error("Nome inválido");
                return false;
            }

            if (produto.getCategoria() == null) {
                logger.error("Categoria não informada");
                return false;
            }

            if (produto.getPreco() == null || produto.getPreco() <= 0) {
                logger.error("Preço inválido");
                return false;
            }

            if (produto.getQuantidade() == null || produto.getQuantidade() < 0) {
                logger.error("Quantidade inválida");
                return false;
            }

            boolean resultado = produtoDAO.salvar(produto);

            if (resultado) {
                logger.info("Produto salvo com sucesso");
            } else {
                logger.error("Falha ao salvar Produto");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao salvar Produto: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Produto produto) {
        logger.info("Iniciando alterar Produto");

        try {
            boolean resultado = produtoDAO.alterar(produto);

            if (resultado) {
                logger.info("Produto alterado com sucesso");
            } else {
                logger.error("Falha ao alterar Produto");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao alterar Produto: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        logger.info("Iniciando excluir Produto id=" + id);

        try {
            boolean resultado = produtoDAO.excluir(id);

            if (resultado) {
                logger.info("Produto excluído com sucesso");
            } else {
                logger.error("Falha ao excluir Produto");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao excluir Produto: " + e.getMessage());
            return false;
        }
    }

    public Produto pesquisar(int id) {
        logger.info("Iniciando pesquisar Produto id=" + id);

        try {
            Produto produto = produtoDAO.pesquisarPorId(id);

            if (produto != null) {
                logger.info("Produto encontrado");
            } else {
                logger.error("Produto não encontrado");
            }

            return produto;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar Produto: " + e.getMessage());
            return null;
        }
    }

    public List<Produto> pesquisarTodos() {
        logger.info("Iniciando pesquisar todos os Produtos");

        try {
            List<Produto> produtos = produtoDAO.pesquisar();
            logger.info("Quantidade encontrada: " + produtos.size());
            return produtos;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar Produtos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean verificaEstoqueExistente(
            Produto produto,
            int quantidadeDesejada) {

        logger.info("Verificando estoque do Produto");

        try {
            Produto produtoExistente =
                    produtoDAO.pesquisarPorId(produto.getId());

            if (produtoExistente == null) {
                logger.error("Produto não encontrado");
                return false;
            }

            return produtoExistente.getQuantidade() >= quantidadeDesejada;

        } catch (Exception e) {
            logger.error("Erro ao verificar estoque: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarEstoqueVenda(Produto produto, int quantidade) {
        logger.info("Atualizando estoque após venda");

        try {
            Produto produtoExistente =
                    produtoDAO.pesquisarPorId(produto.getId());

            if (produtoExistente == null) {
                logger.error("Produto não encontrado");
                return false;
            }

            int novoEstoque =
                    produtoExistente.getQuantidade() - quantidade;

            if (novoEstoque < 0) {
                logger.error("Estoque insuficiente");
                return false;
            }

            produtoExistente.setQuantidade(novoEstoque);

            return produtoDAO.alterar(produtoExistente);

        } catch (Exception e) {
            logger.error("Erro ao atualizar estoque venda: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarEstoqueCompra(Produto produto, int quantidade) {
        logger.info("Atualizando estoque após compra");

        try {
            Produto produtoExistente =
                    produtoDAO.pesquisarPorId(produto.getId());

            if (produtoExistente == null) {
                logger.error("Produto não encontrado");
                return false;
            }

            produtoExistente.setQuantidade(
                    produtoExistente.getQuantidade() + quantidade
            );

            return produtoDAO.alterar(produtoExistente);

        } catch (Exception e) {
            logger.error("Erro ao atualizar estoque compra: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarUltimaVenda(Produto produto, Double valor) {
        logger.info("Atualizando última venda");

        try {
            Produto produtoExistente =
                    produtoDAO.pesquisarPorId(produto.getId());

            if (produtoExistente == null) {
                logger.error("Produto não encontrado");
                return false;
            }

            produtoExistente.setValorUltimaVenda(valor);

            return produtoDAO.alterar(produtoExistente);

        } catch (Exception e) {
            logger.error("Erro ao atualizar última venda: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarUltimaCompra(Produto produto, Double valor) {
        logger.info("Atualizando última compra");

        try {
            Produto produtoExistente =
                    produtoDAO.pesquisarPorId(produto.getId());

            if (produtoExistente == null) {
                logger.error("Produto não encontrado");
                return false;
            }

            produtoExistente.setValorUltimaCompra(valor);

            return produtoDAO.alterar(produtoExistente);

        } catch (Exception e) {
            logger.error("Erro ao atualizar última compra: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarPrecoMedio(Produto produto, Double valorCompra) {
        logger.info("Atualizando preço médio");

        try {
            Produto produtoExistente =
                    produtoDAO.pesquisarPorId(produto.getId());

            if (produtoExistente == null) {
                logger.error("Produto não encontrado");
                return false;
            }

            Double precoMedioAtual =
                    produtoExistente.getPrecoMedio();

            if (precoMedioAtual == null) {
                produtoExistente.setPrecoMedio(valorCompra);
            } else {
                produtoExistente.setPrecoMedio(
                        (precoMedioAtual + valorCompra) / 2
                );
            }

            return produtoDAO.alterar(produtoExistente);

        } catch (Exception e) {
            logger.error("Erro ao atualizar preço médio: " + e.getMessage());
            return false;
        }
    }
}