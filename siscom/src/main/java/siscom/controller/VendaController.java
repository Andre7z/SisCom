package siscom.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import siscom.dao.VendaDAO;
import siscom.model.Financeiro;
import siscom.model.FormaPagamento;
import siscom.model.TipoConta;
import siscom.model.Venda;
import siscom.model.VendaProduto;
import siscom.model.FinanceiroParcela;

public class VendaController {

    private static final Logger logger = LogManager.getLogger(VendaController.class);

    private VendaDAO vendaDAO = new VendaDAO();
    private ProdutoController produtoController = new ProdutoController();
    private FinanceiroController financeiroController = new FinanceiroController();
    private FinanceiroParcelaController financeiroParcelaController = new FinanceiroParcelaController();

    public boolean salvar(
            Venda venda,
            FormaPagamento formaPagamento,
            TipoConta tipoConta) {

        logger.info("Iniciando salvar Venda");

        try {

            if (venda == null) {
                logger.error("Venda nula");
                return false;
            }

            if (venda.getCliente() == null) {
                logger.error("Cliente não informado");
                return false;
            }

            if (venda.getDataVenda() == null) {
                logger.error("Data da venda não informada");
                return false;
            }

            if (venda.getProdutos() == null ||
                    venda.getProdutos().isEmpty()) {
                logger.error("Venda sem produtos");
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

            int quantidadeVendas = vendaDAO.contarVendasPorCpfMes(
                    venda.getCliente().getCpf());

            if (quantidadeVendas >= 3) {
                logger.error("CPF excedeu limite de vendas no mês");
                return false;
            }

            double valorTotal = 0;

            for (VendaProduto item : venda.getProdutos()) {

                if (item == null) {
                    logger.error("Item da venda nulo");
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

                if (!produtoController.verificaEstoqueExistente(
                        item.getProduto(),
                        item.getQuantidade())) {

                    logger.error("Produto sem estoque suficiente");
                    return false;
                }

                if (!produtoController.atualizarEstoqueVenda(
                        item.getProduto(),
                        item.getQuantidade())) {

                    logger.error("Erro ao atualizar estoque");
                    return false;
                }

                if (!produtoController.atualizarUltimaVenda(
                        item.getProduto(),
                        item.getValorUnitario())) {

                    logger.error("Erro ao atualizar valor da última venda");
                    return false;
                }

                item.setVenda(venda);

                valorTotal += item.getQuantidade() * item.getValorUnitario();
            }

            venda.setValorTotal(valorTotal);

            if (!vendaDAO.salvar(venda)) {
                logger.error("Falha ao salvar venda");
                return false;
            }

            // Financeiro

            Financeiro financeiro = new Financeiro();

            financeiro.setDataConta(venda.getDataVenda());
            financeiro.setValorTotal(venda.getValorTotal());
            financeiro.setPagarOuReceber(1); // Receber
            financeiro.setFormaPagamento(formaPagamento);
            financeiro.setTipoConta(tipoConta);
            financeiro.setCliente(venda.getCliente());

            if (!financeiroController.salvar(financeiro)) {
                logger.error("Erro ao gerar financeiro da venda");
                vendaDAO.excluir(venda.getId());
                return false;
            }

            // Parcelas

            int qtdeParcelas = formaPagamento.getQtdeParcela();

            if (qtdeParcelas <= 0) {
                qtdeParcelas = 1;
            }

            double valorParcela = venda.getValorTotal() / qtdeParcelas;

            for (int i = 1; i <= qtdeParcelas; i++) {

                FinanceiroParcela parcela = new FinanceiroParcela();

                parcela.setFinanceiro(financeiro);
                parcela.setNParcela(i);

                parcela.setDataVencimento(
                        venda.getDataVenda().plusDays(
                                (long) formaPagamento.getPrazo() * i));

                parcela.setDataPagamento(null);

                parcela.setValorOriginal(valorParcela);
                parcela.setDesconto(0.0);
                parcela.setAcrescimo(0.0);
                parcela.setValorFinal(valorParcela);

                parcela.setStatus(0); // Aberta

                if (!financeiroParcelaController.salvar(parcela)) {
                    logger.error("Erro ao salvar parcela " + i);
                    return false;
                }
            }

            venda.setFinanceiro(financeiro);

            if (!vendaDAO.alterar(venda)) {
                logger.error("Erro ao vincular financeiro à venda");
                return false;
            }

            logger.info("Venda salva com sucesso");

            return true;

        } catch (Exception e) {

            logger.error("Erro ao salvar Venda", e);

            return false;
        }
    }

    public boolean alterar(Venda venda) {
        logger.info("Iniciando alterar Venda");

        try {
            boolean resultado = vendaDAO.alterar(venda);

            if (resultado) {
                logger.info("Venda alterada com sucesso");
            } else {
                logger.error("Falha ao alterar venda");
            }

            return resultado;
        } catch (Exception e) {
            logger.error("Erro ao alterar Venda: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        logger.info("Iniciando excluir Venda id=" + id);

        try {
            boolean resultado = vendaDAO.excluir(id);

            if (resultado) {
                logger.info("Venda excluída com sucesso");
            } else {
                logger.error("Falha ao excluir venda");
            }

            return resultado;
        } catch (Exception e) {
            logger.error("Erro ao excluir Venda: " + e.getMessage());
            return false;
        }
    }

    public Venda pesquisar(int id) {
        logger.info("Iniciando pesquisar Venda id=" + id);

        try {
            Venda venda = vendaDAO.pesquisarPorId(id);

            if (venda != null) {
                logger.info("Venda encontrada");
            } else {
                logger.error("Venda não encontrada");
            }

            return venda;
        } catch (Exception e) {
            logger.error("Erro ao pesquisar Venda: " + e.getMessage());
            return null;
        }
    }

    public List<Venda> pesquisarTodos() {
        logger.info("Iniciando pesquisar todas as vendas");

        try {
            List<Venda> vendas = vendaDAO.pesquisar();
            logger.info("Quantidade encontrada: " + vendas.size());
            return vendas;
        } catch (Exception e) {
            logger.error("Erro ao pesquisar vendas: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}