package siscom.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import siscom.dao.CompraDAO;
import siscom.model.Compra;
import siscom.model.CompraProduto;
import siscom.model.Financeiro;

public class CompraController {

    private static final Logger logger =
            LogManager.getLogger(CompraController.class);

    private CompraDAO compraDAO = new CompraDAO();
    private ProdutoController produtoController = new ProdutoController();
    private FinanceiroController financeiroController =
            new FinanceiroController();

    public boolean salvar(Compra compra) {
        logger.info("Iniciando salvar Compra");

        try {
            if (compra == null) {
                logger.error("Compra nula");
                return false;
            }

            if (compra.getFornecedor() == null) {
                logger.error("Fornecedor não informado");
                return false;
            }

            if (compra.getProdutos() == null ||
                compra.getProdutos().isEmpty()) {
                logger.error("Compra sem produtos");
                return false;
            }

            double valorTotal = 0;

            for (CompraProduto item : compra.getProdutos()) {

                if (item.getProduto() == null) {
                    logger.error("Produto inválido");
                    return false;
                }

                item.setCompra(compra);

                // RNF-P002 → aumenta estoque
                boolean estoqueOk =
                        produtoController.atualizarEstoqueCompra(
                                item.getProduto(),
                                item.getQuantidade());

                if (!estoqueOk) {
                    logger.error("Erro ao atualizar estoque");
                    return false;
                }

                // RNF-P006
                produtoController.atualizarUltimaCompra(
                        item.getProduto(),
                        item.getValorUnitario());

                // RNF-P007
                produtoController.atualizarPrecoMedio(
                        item.getProduto(),
                        item.getValorUnitario());

                valorTotal +=
                        item.getQuantidade() * item.getValorUnitario();
            }

            compra.setValorTotal(valorTotal);
            compra.setDataCompra(LocalDate.now());

            boolean compraSalva = compraDAO.salvar(compra);

            if (!compraSalva) {
                logger.error("Falha ao salvar compra");
                return false;
            }

            // RF009 / RNF-P009 / RNF-P010
            Financeiro financeiro = new Financeiro();
            financeiro.setDataConta(LocalDate.now());
            financeiro.setPagarOuReceber(0); // conta a pagar

            // Ajuste conforme teu model Compra
            // financeiro.setFormaPagamento(compra.getFormaPagamento());
            // financeiro.setTipoConta(...);

            financeiroController.salvar(financeiro);

            logger.info("Compra salva com sucesso");
            return true;

        } catch (Exception e) {
            logger.error("Erro ao salvar Compra: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Compra compra) {
        logger.info("Iniciando alterar Compra");

        try {
            boolean resultado = compraDAO.alterar(compra);

            if (resultado) {
                logger.info("Compra alterada com sucesso");
            } else {
                logger.error("Falha ao alterar compra");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao alterar Compra: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        logger.info("Iniciando excluir Compra id=" + id);

        try {
            boolean resultado = compraDAO.excluir(id);

            if (resultado) {
                logger.info("Compra excluída com sucesso");
            } else {
                logger.error("Falha ao excluir compra");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao excluir Compra: " + e.getMessage());
            return false;
        }
    }

    public Compra pesquisar(int id) {
        logger.info("Iniciando pesquisar Compra id=" + id);

        try {
            Compra compra = compraDAO.pesquisarPorId(id);

            if (compra != null) {
                logger.info("Compra encontrada");
            } else {
                logger.error("Compra não encontrada");
            }

            return compra;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar Compra: " + e.getMessage());
            return null;
        }
    }

    public List<Compra> pesquisarTodos() {
        logger.info("Iniciando pesquisar todas as compras");

        try {
            List<Compra> compras = compraDAO.pesquisar();
            logger.info("Quantidade encontrada: " + compras.size());
            return compras;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar compras: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}