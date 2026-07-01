package siscom.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import siscom.dao.TipoContaDAO;
import siscom.model.TipoConta;

public class TipoContaController {

    private static final Logger logger =
            LogManager.getLogger(TipoContaController.class);

    private TipoContaDAO tipoContaDAO = new TipoContaDAO();

    public boolean salvar(TipoConta tipoConta) {
        logger.info("Iniciando salvar TipoConta");

        try {
            if (tipoConta == null) {
                logger.error("TipoConta nulo");
                return false;
            }

            if (tipoConta.getDescricao() == null ||
                tipoConta.getDescricao().isBlank()) {
                logger.error("Descrição inválida");
                return false;
            }

            boolean resultado = tipoContaDAO.salvar(tipoConta);

            if (resultado) {
                logger.info("TipoConta salvo com sucesso");
            } else {
                logger.error("Falha ao salvar TipoConta");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao salvar TipoConta: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(TipoConta tipoConta) {
        logger.info("Iniciando alterar TipoConta");

        try {
            if (tipoConta == null) {
                logger.error("TipoConta nulo");
                return false;
            }

            boolean resultado = tipoContaDAO.alterar(tipoConta);

            if (resultado) {
                logger.info("TipoConta alterado com sucesso");
            } else {
                logger.error("Falha ao alterar TipoConta");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao alterar TipoConta: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        logger.info("Iniciando excluir TipoConta id=" + id);

        try {
            boolean resultado = tipoContaDAO.excluir(id);

            if (resultado) {
                logger.info("TipoConta excluído com sucesso");
            } else {
                logger.error("Falha ao excluir TipoConta");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao excluir TipoConta: " + e.getMessage());
            return false;
        }
    }

    public TipoConta pesquisar(int id) {
        logger.info("Iniciando pesquisar TipoConta id=" + id);

        try {
            TipoConta tipoConta = tipoContaDAO.pesquisarPorId(id);

            if (tipoConta != null) {
                logger.info("TipoConta encontrado");
            } else {
                logger.error("TipoConta não encontrado");
            }

            return tipoConta;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar TipoConta: " + e.getMessage());
            return null;
        }
    }

    public List<TipoConta> pesquisarTodos() {
        logger.info("Iniciando pesquisar todos os TipoConta");

        try {
            List<TipoConta> tipos = tipoContaDAO.pesquisar();
            logger.info("Quantidade encontrada: " + tipos.size());
            return tipos;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar TipoConta: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}