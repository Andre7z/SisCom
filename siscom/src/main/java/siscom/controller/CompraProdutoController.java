package siscom.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import siscom.dao.CompraProdutoDAO;
import siscom.model.CompraProduto;

public class CompraProdutoController {

    private static final Logger logger =
            LogManager.getLogger(CompraProdutoController.class);

    private CompraProdutoDAO compraProdutoDAO =
            new CompraProdutoDAO();

    public boolean salvar(CompraProduto compraProduto) {
        logger.info("Iniciando salvar CompraProduto");

        try {
            if (compraProduto == null) {
                logger.error("CompraProduto nulo");
                return false;
            }

            if (compraProduto.getCompra() == null) {
                logger.error("Compra não informada");
                return false;
            }

            if (compraProduto.getProduto() == null) {
                logger.error("Produto não informado");
                return false;
            }

            boolean resultado = compraProdutoDAO.salvar(compraProduto);

            if (resultado) {
                logger.info("CompraProduto salvo com sucesso");
            } else {
                logger.error("Falha ao salvar CompraProduto");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao salvar CompraProduto: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(CompraProduto compraProduto) {
        logger.info("Iniciando alterar CompraProduto");

        try {
            boolean resultado = compraProdutoDAO.alterar(compraProduto);

            if (resultado) {
                logger.info("CompraProduto alterado com sucesso");
            } else {
                logger.error("Falha ao alterar CompraProduto");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao alterar CompraProduto: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        logger.info("Iniciando excluir CompraProduto id=" + id);

        try {
            boolean resultado = compraProdutoDAO.excluir(id);

            if (resultado) {
                logger.info("CompraProduto excluído com sucesso");
            } else {
                logger.error("Falha ao excluir CompraProduto");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao excluir CompraProduto: " + e.getMessage());
            return false;
        }
    }

    public CompraProduto pesquisar(int id) {
        logger.info("Iniciando pesquisar CompraProduto id=" + id);

        try {
            CompraProduto compraProduto =
                    compraProdutoDAO.pesquisarPorId(id);

            if (compraProduto != null) {
                logger.info("CompraProduto encontrado");
            } else {
                logger.error("CompraProduto não encontrado");
            }

            return compraProduto;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar CompraProduto: " + e.getMessage());
            return null;
        }
    }

    public List<CompraProduto> pesquisarTodos() {
        logger.info("Iniciando pesquisar todos os CompraProduto");

        try {
            List<CompraProduto> lista =
                    compraProdutoDAO.pesquisar();

            logger.info("Quantidade encontrada: " + lista.size());
            return lista;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar CompraProduto: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}