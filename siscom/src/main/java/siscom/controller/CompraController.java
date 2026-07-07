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
import siscom.model.FormaPagamento;
import siscom.model.TipoConta;

public class CompraController {

    private static final Logger logger = LogManager.getLogger(CompraController.class);

    private CompraDAO compraDAO = new CompraDAO();
    private ProdutoController produtoController = new ProdutoController();
    private FinanceiroController financeiroController = new FinanceiroController();

    public boolean salvar(
            Compra compra,
            FormaPagamento formaPagamento,
            TipoConta tipoConta) {

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

            if (formaPagamento == null) {
                logger.error("Forma de pagamento não informada");
                return false;
            }

            if (tipoConta == null) {
                logger.error("Tipo de conta não informado");
                return false;
            }

            double valorTotal = 0;

            for (CompraProduto item : compra.getProdutos()) {

                if (item == null) {
                    logger.error("Item da compra nulo");
                    return false;
                }

                if (item.getProduto() == null) {
                    logger.error("Produto inválido");
                    return false;
                }

                if (item.getQuantidade() <= 0) {
                    logger.error("Quantidade inválida");
                    return false;
                }

                if (item.getValorUnitario() == null ||
                        item.getValorUnitario() <= 0) {
                    logger.error("Valor unitário inválido");
                    return false;
                }

                item.setCompra(compra);

                boolean estoqueOk = produtoController.atualizarEstoqueCompra(
                        item.getProduto(),
                        item.getQuantidade());

                if (!estoqueOk) {
                    logger.error("Erro ao atualizar estoque");
                    return false;
                }

                boolean ultimaCompraOk = produtoController.atualizarUltimaCompra(
                        item.getProduto(),
                        item.getValorUnitario());

                if (!ultimaCompraOk) {
                    logger.error("Erro ao atualizar última compra");
                    return false;
                }

                boolean precoMedioOk = produtoController.atualizarPrecoMedio(
                        item.getProduto(),
                        item.getValorUnitario());

                if (!precoMedioOk) {
                    logger.error("Erro ao atualizar preço médio");
                    return false;
                }

                valorTotal += item.getQuantidade() * item.getValorUnitario();
            }

            compra.setValorTotal(valorTotal);
            compra.setDataCompra(LocalDate.now());

            boolean compraSalva = compraDAO.salvar(compra);

            if (!compraSalva) {
                logger.error("Falha ao salvar compra");
                return false;
            }

            Financeiro financeiro = new Financeiro();

            financeiro.setDataConta(LocalDate.now());
            financeiro.setValorTotal(compra.getValorTotal()); // NOVO
            financeiro.setPagarOuReceber(0);
            financeiro.setFormaPagamento(formaPagamento);
            financeiro.setTipoConta(tipoConta);
            financeiro.setFornecedor(compra.getFornecedor());

            boolean financeiroSalvo = financeiroController.salvar(financeiro);

            if (!financeiroSalvo) {
                logger.error("Falha ao gerar financeiro");
                compraDAO.excluir(compra.getId());
                return false;
            }

            compra.setFinanceiro(financeiro);

            boolean vinculoOk = compraDAO.alterar(compra);

            if (!vinculoOk) {
                logger.error("Falha ao vincular financeiro");
                return false;
            }

            logger.info("Compra salva com sucesso id=" + compra.getId());
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