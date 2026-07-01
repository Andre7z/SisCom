package siscom.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import siscom.dao.FornecedorProdutoDAO;
import siscom.model.FornecedorProduto;

public class FornecedorProdutoController {

    private static final Logger logger =
            LogManager.getLogger(FornecedorProdutoController.class);

    private FornecedorProdutoDAO fornecedorProdutoDAO =
            new FornecedorProdutoDAO();

    public boolean salvar(FornecedorProduto fornecedorProduto) {
        logger.info("Iniciando salvar FornecedorProduto");

        try {
            if (fornecedorProduto == null) {
                logger.error("FornecedorProduto nulo");
                return false;
            }

            if (fornecedorProduto.getFornecedor() == null) {
                logger.error("Fornecedor não informado");
                return false;
            }

            if (fornecedorProduto.getProduto() == null) {
                logger.error("Produto não informado");
                return false;
            }

            boolean resultado = fornecedorProdutoDAO.salvar(fornecedorProduto);

            if (resultado) {
                logger.info("FornecedorProduto salvo com sucesso");
            } else {
                logger.error("Falha ao salvar FornecedorProduto");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao salvar FornecedorProduto: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(FornecedorProduto fornecedorProduto) {
        logger.info("Iniciando alterar FornecedorProduto");

        try {
            boolean resultado = fornecedorProdutoDAO.alterar(fornecedorProduto);

            if (resultado) {
                logger.info("FornecedorProduto alterado com sucesso");
            } else {
                logger.error("Falha ao alterar FornecedorProduto");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao alterar FornecedorProduto: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        logger.info("Iniciando excluir FornecedorProduto id=" + id);

        try {
            boolean resultado = fornecedorProdutoDAO.excluir(id);

            if (resultado) {
                logger.info("FornecedorProduto excluído com sucesso");
            } else {
                logger.error("Falha ao excluir FornecedorProduto");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao excluir FornecedorProduto: " + e.getMessage());
            return false;
        }
    }

    public FornecedorProduto pesquisar(int id) {
        logger.info("Iniciando pesquisar FornecedorProduto id=" + id);

        try {
            FornecedorProduto fornecedorProduto =
                    fornecedorProdutoDAO.pesquisarPorId(id);

            if (fornecedorProduto != null) {
                logger.info("FornecedorProduto encontrado");
            } else {
                logger.error("FornecedorProduto não encontrado");
            }

            return fornecedorProduto;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar FornecedorProduto: " + e.getMessage());
            return null;
        }
    }

    public List<FornecedorProduto> pesquisarTodos() {
        logger.info("Iniciando pesquisar todos os FornecedorProduto");

        try {
            List<FornecedorProduto> lista =
                    fornecedorProdutoDAO.pesquisar();

            logger.info("Quantidade encontrada: " + lista.size());
            return lista;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar FornecedorProduto: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}