package siscom.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import siscom.dao.FinanceiroDAO;
import siscom.model.Financeiro;
import siscom.model.FinanceiroParcela;

public class FinanceiroController {

    private static final Logger logger =
            LogManager.getLogger(FinanceiroController.class);

    private FinanceiroDAO financeiroDAO = new FinanceiroDAO();

    public boolean salvar(Financeiro financeiro) {
        logger.info("Iniciando salvar Financeiro");

        try {
            if (financeiro == null) {
                logger.error("Financeiro nulo");
                return false;
            }

            if (financeiro.getDataConta() == null) {
                logger.error("Data da conta inválida");
                return false;
            }

            if (financeiro.getTipoConta() == null) {
                logger.error("Tipo de conta não informado");
                return false;
            }

            if (financeiro.getFormaPagamento() == null) {
                logger.error("Forma de pagamento não informada");
                return false;
            }

            // RNF-P010
            if (financeiro.getPagarOuReceber() != 0 &&
                financeiro.getPagarOuReceber() != 1) {
                logger.error("Campo pagarOuReceber deve ser 0 ou 1");
                return false;
            }

            // Conta a pagar precisa de fornecedor
            if (financeiro.getPagarOuReceber() == 0 &&
                financeiro.getFornecedor() == null) {
                logger.error("Fornecedor não informado");
                return false;
            }

            // Conta a receber precisa de cliente
            if (financeiro.getPagarOuReceber() == 1 &&
                financeiro.getCliente() == null) {
                logger.error("Cliente não informado");
                return false;
            }

            if (financeiro.getParcelas() != null &&
                !financeiro.getParcelas().isEmpty()) {

                for (FinanceiroParcela parcela : financeiro.getParcelas()) {

                    if (parcela == null) {
                        logger.error("Parcela nula");
                        return false;
                    }

                    if (parcela.getNParcela() <= 0) {
                        logger.error("Número da parcela inválido");
                        return false;
                    }

                    if (parcela.getDataVencimento() == null) {
                        logger.error("Data de vencimento inválida");
                        return false;
                    }

                    parcela.setFinanceiro(financeiro);
                }
            }

            boolean resultado = financeiroDAO.salvar(financeiro);

            if (resultado) {
                logger.info("Financeiro salvo com sucesso");
            } else {
                logger.error("Falha ao salvar Financeiro");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao salvar Financeiro", e);
            return false;
        }
    }

    public boolean alterar(Financeiro financeiro) {
        logger.info("Iniciando alterar Financeiro");

        try {
            boolean resultado = financeiroDAO.alterar(financeiro);

            if (resultado) {
                logger.info("Financeiro alterado com sucesso");
            } else {
                logger.error("Falha ao alterar Financeiro");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao alterar Financeiro", e);
            return false;
        }
    }

    public boolean excluir(int id) {
        logger.info("Iniciando excluir Financeiro id=" + id);

        try {
            boolean resultado = financeiroDAO.excluir(id);

            if (resultado) {
                logger.info("Financeiro excluído com sucesso");
            } else {
                logger.error("Falha ao excluir Financeiro");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao excluir Financeiro", e);
            return false;
        }
    }

    public Financeiro pesquisar(int id) {
        logger.info("Iniciando pesquisar Financeiro id=" + id);

        try {
            Financeiro financeiro = financeiroDAO.pesquisarPorId(id);

            if (financeiro != null) {
                logger.info("Financeiro encontrado");
            } else {
                logger.error("Financeiro não encontrado");
            }

            return financeiro;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar Financeiro", e);
            return null;
        }
    }

    public List<Financeiro> pesquisarTodos() {
        logger.info("Iniciando pesquisar todos os Financeiros");

        try {
            List<Financeiro> lista = financeiroDAO.pesquisar();
            logger.info("Quantidade encontrada: " + lista.size());
            return lista;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar Financeiros", e);
            return new ArrayList<>();
        }
    }
}