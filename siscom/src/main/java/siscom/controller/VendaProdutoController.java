package siscom.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import siscom.dao.VendaProdutoDAO;
import siscom.model.VendaProduto;

public class VendaProdutoController {

    private static final Logger logger =
            LogManager.getLogger(VendaProdutoController.class);

    VendaProdutoDAO vendaProdutoDAO = new VendaProdutoDAO();

    public boolean salvar(VendaProduto vendaProduto) {
        logger.info("Iniciando salvar VendaProduto");

        try {
            boolean resultado = vendaProdutoDAO.salvar(vendaProduto);

            if (resultado) {
                logger.info("VendaProduto salvo com sucesso");
            } else {
                logger.error("Falha ao salvar VendaProduto");
            }

            return resultado;
        } catch (Exception e) {
            logger.error("Erro ao salvar VendaProduto: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(VendaProduto vendaProduto) {
        logger.info("Iniciando alterar VendaProduto");

        try {
            boolean resultado = vendaProdutoDAO.alterar(vendaProduto);

            if (resultado) {
                logger.info("VendaProduto alterado com sucesso");
            } else {
                logger.error("Falha ao alterar VendaProduto");
            }

            return resultado;
        } catch (Exception e) {
            logger.error("Erro ao alterar VendaProduto: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        logger.info("Iniciando excluir VendaProduto id=" + id);

        try {
            boolean resultado = vendaProdutoDAO.excluir(id);

            if (resultado) {
                logger.info("VendaProduto excluído com sucesso");
            } else {
                logger.error("Falha ao excluir VendaProduto");
            }

            return resultado;
        } catch (Exception e) {
            logger.error("Erro ao excluir VendaProduto: " + e.getMessage());
            return false;
        }
    }

    public VendaProduto pesquisar(int id) {
        logger.info("Iniciando pesquisar VendaProduto id=" + id);

        try {
            VendaProduto vendaProduto = vendaProdutoDAO.pesquisarPorId(id);

            if (vendaProduto != null) {
                logger.info("VendaProduto encontrado");
            } else {
                logger.error("VendaProduto não encontrado");
            }

            return vendaProduto;
        } catch (Exception e) {
            logger.error("Erro ao pesquisar VendaProduto: " + e.getMessage());
            return null;
        }
    }

    public List<VendaProduto> pesquisarTodos() {
        logger.info("Iniciando pesquisarTodos VendaProduto");

        try {
            List<VendaProduto> lista = vendaProdutoDAO.pesquisar();
            logger.info("Quantidade encontrada: " + lista.size());
            return lista;
        } catch (Exception e) {
            logger.error("Erro ao pesquisar todos VendaProduto: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}