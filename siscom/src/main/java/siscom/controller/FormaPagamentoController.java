package siscom.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import siscom.dao.FormaPagamentoDAO;
import siscom.model.FormaPagamento;

public class FormaPagamentoController {

    private static final Logger logger =
            LogManager.getLogger(FormaPagamentoController.class);

    private FormaPagamentoDAO formaPagamentoDAO =
            new FormaPagamentoDAO();

    public boolean salvar(FormaPagamento formaPagamento) {
        logger.info("Iniciando salvar FormaPagamento");

        try {
            if (formaPagamento == null) {
                logger.error("FormaPagamento nula");
                return false;
            }

            boolean resultado =
                    formaPagamentoDAO.salvar(formaPagamento);

            if (resultado) {
                logger.info("FormaPagamento salva com sucesso");
            } else {
                logger.error("Falha ao salvar FormaPagamento");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao salvar FormaPagamento: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(FormaPagamento formaPagamento) {
        logger.info("Iniciando alterar FormaPagamento");

        try {
            boolean resultado =
                    formaPagamentoDAO.alterar(formaPagamento);

            if (resultado) {
                logger.info("FormaPagamento alterada com sucesso");
            } else {
                logger.error("Falha ao alterar FormaPagamento");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao alterar FormaPagamento: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        logger.info("Iniciando excluir FormaPagamento id=" + id);

        try {
            boolean resultado =
                    formaPagamentoDAO.excluir(id);

            if (resultado) {
                logger.info("FormaPagamento excluída com sucesso");
            } else {
                logger.error("Falha ao excluir FormaPagamento");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao excluir FormaPagamento: " + e.getMessage());
            return false;
        }
    }

    public FormaPagamento pesquisar(int id) {
        logger.info("Iniciando pesquisar FormaPagamento id=" + id);

        try {
            FormaPagamento formaPagamento =
                    formaPagamentoDAO.pesquisarPorId(id);

            if (formaPagamento != null) {
                logger.info("FormaPagamento encontrada");
            } else {
                logger.error("FormaPagamento não encontrada");
            }

            return formaPagamento;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar FormaPagamento: " + e.getMessage());
            return null;
        }
    }

    public List<FormaPagamento> pesquisarTodos() {
        logger.info("Iniciando pesquisar todas as FormaPagamento");

        try {
            List<FormaPagamento> formas =
                    formaPagamentoDAO.pesquisar();

            logger.info("Quantidade encontrada: " + formas.size());
            return formas;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar FormaPagamento: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}