package siscom.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import siscom.dao.FinanceiroParcelaDAO;
import siscom.model.FinanceiroParcela;

public class FinanceiroParcelaController {

    private static final Logger logger =
            LogManager.getLogger(FinanceiroParcelaController.class);

    private FinanceiroParcelaDAO financeiroParcelaDAO =
            new FinanceiroParcelaDAO();

    public boolean salvar(FinanceiroParcela financeiroParcela) {
        logger.info("Iniciando salvar FinanceiroParcela");

        try {
            if (financeiroParcela == null) {
                logger.error("FinanceiroParcela nula");
                return false;
            }

            if (financeiroParcela.getFinanceiro() == null) {
                logger.error("Financeiro não informado");
                return false;
            }

            boolean resultado =
                    financeiroParcelaDAO.salvar(financeiroParcela);

            if (resultado) {
                logger.info("FinanceiroParcela salva com sucesso");
            } else {
                logger.error("Falha ao salvar FinanceiroParcela");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao salvar FinanceiroParcela: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(FinanceiroParcela financeiroParcela) {
        logger.info("Iniciando alterar FinanceiroParcela");

        try {
            boolean resultado =
                    financeiroParcelaDAO.alterar(financeiroParcela);

            if (resultado) {
                logger.info("FinanceiroParcela alterada com sucesso");
            } else {
                logger.error("Falha ao alterar FinanceiroParcela");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao alterar FinanceiroParcela: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        logger.info("Iniciando excluir FinanceiroParcela id=" + id);

        try {
            boolean resultado =
                    financeiroParcelaDAO.excluir(id);

            if (resultado) {
                logger.info("FinanceiroParcela excluída com sucesso");
            } else {
                logger.error("Falha ao excluir FinanceiroParcela");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao excluir FinanceiroParcela: " + e.getMessage());
            return false;
        }
    }

    public FinanceiroParcela pesquisar(int id) {
        logger.info("Iniciando pesquisar FinanceiroParcela id=" + id);

        try {
            FinanceiroParcela financeiroParcela =
                    financeiroParcelaDAO.pesquisarPorId(id);

            if (financeiroParcela != null) {
                logger.info("FinanceiroParcela encontrada");
            } else {
                logger.error("FinanceiroParcela não encontrada");
            }

            return financeiroParcela;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar FinanceiroParcela: " + e.getMessage());
            return null;
        }
    }

    public List<FinanceiroParcela> pesquisarTodos() {
        logger.info("Iniciando pesquisar todas as FinanceiroParcela");

        try {
            List<FinanceiroParcela> parcelas =
                    financeiroParcelaDAO.pesquisar();

            logger.info("Quantidade encontrada: " + parcelas.size());
            return parcelas;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar FinanceiroParcela: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}