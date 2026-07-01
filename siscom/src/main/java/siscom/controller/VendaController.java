package siscom.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import siscom.dao.VendaDAO;
import siscom.model.Venda;
import siscom.model.VendaProduto;

public class VendaController {

    private static final Logger logger =
            LogManager.getLogger(VendaController.class);

    private VendaDAO vendaDAO = new VendaDAO();
    private ProdutoController produtoController = new ProdutoController();

    public boolean salvar(Venda venda) {
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

            if (venda.getVendaProdutos() == null ||
                venda.getVendaProdutos().isEmpty()) {
                logger.error("Venda sem produtos");
                return false;
            }

            int quantidadeVendas =
                    vendaDAO.contarVendasPorCpfMes(
                            venda.getCliente().getCpf());

            if (quantidadeVendas >= 3) {
                logger.error("CPF excedeu limite de vendas no mês");
                return false;
            }

            double valorTotal = 0;

            for (VendaProduto item : venda.getVendaProdutos()) {

                if (item.getProduto() == null) {
                    logger.error("Produto inválido");
                    return false;
                }

                boolean estoqueOk =
                        produtoController.verificaEstoqueExistente(
                                item.getProduto());

                if (!estoqueOk) {
                    logger.error("Produto sem estoque");
                    return false;
                }

                boolean estoqueAtualizado =
                        produtoController.atualizarEstoqueVenda(
                                item.getProduto(),
                                item.getQuantidade());

                if (!estoqueAtualizado) {
                    logger.error("Erro ao atualizar estoque");
                    return false;
                }

                boolean ultimaVendaAtualizada =
                        produtoController.atualizarUltimaVenda(
                                item.getProduto(),
                                item.getValorUnitario());

                if (!ultimaVendaAtualizada) {
                    logger.error("Erro ao atualizar valor da última venda");
                    return false;
                }

                item.setVenda(venda);

                valorTotal +=
                        item.getQuantidade() * item.getValorUnitario();
            }

            venda.setValorTotal(valorTotal);

            boolean resultado = vendaDAO.salvar(venda);

            if (resultado) {
                logger.info("Venda salva com sucesso");
            } else {
                logger.error("Falha ao salvar venda");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao salvar Venda: " + e.getMessage());
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