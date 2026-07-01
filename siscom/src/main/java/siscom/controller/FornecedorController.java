package siscom.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import siscom.dao.FornecedorDAO;
import siscom.model.Fornecedor;

public class FornecedorController {

    private static final Logger logger =
            LogManager.getLogger(FornecedorController.class);

    private FornecedorDAO fornecedorDAO = new FornecedorDAO();

    public boolean salvar(Fornecedor fornecedor) {
        logger.info("Iniciando salvar Fornecedor");

        try {
            if (fornecedor == null) {
                logger.error("Fornecedor nulo");
                return false;
            }

            if (fornecedor.getNomeFantasia() == null ||
                    fornecedor.getNomeFantasia().isBlank()) {
                logger.error("Nome fantasia inválido");
                return false;
            }

            if (fornecedor.getRazaoSocial() == null ||
                    fornecedor.getRazaoSocial().isBlank()) {
                logger.error("Razão social inválida");
                return false;
            }

            if (fornecedor.getCnpj() == null ||
                    fornecedor.getCnpj().isBlank()) {
                logger.error("CNPJ inválido");
                return false;
            }

            boolean resultado = fornecedorDAO.salvar(fornecedor);

            if (resultado) {
                logger.info("Fornecedor salvo com sucesso");
            } else {
                logger.error("Falha ao salvar Fornecedor");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao salvar Fornecedor: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Fornecedor fornecedor) {
        logger.info("Iniciando alterar Fornecedor");

        try {
            boolean resultado = fornecedorDAO.alterar(fornecedor);

            if (resultado) {
                logger.info("Fornecedor alterado com sucesso");
            } else {
                logger.error("Falha ao alterar Fornecedor");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao alterar Fornecedor: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        logger.info("Iniciando excluir Fornecedor id=" + id);

        try {
            boolean resultado = fornecedorDAO.excluir(id);

            if (resultado) {
                logger.info("Fornecedor excluído com sucesso");
            } else {
                logger.error("Falha ao excluir Fornecedor");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao excluir Fornecedor: " + e.getMessage());
            return false;
        }
    }

    public Fornecedor pesquisar(int id) {
        logger.info("Iniciando pesquisar Fornecedor id=" + id);

        try {
            Fornecedor fornecedor = fornecedorDAO.pesquisarPorId(id);

            if (fornecedor != null) {
                logger.info("Fornecedor encontrado");
            } else {
                logger.error("Fornecedor não encontrado");
            }

            return fornecedor;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar Fornecedor: " + e.getMessage());
            return null;
        }
    }

    public List<Fornecedor> pesquisarTodos() {
        logger.info("Iniciando pesquisar todos os Fornecedores");

        try {
            List<Fornecedor> fornecedores = fornecedorDAO.pesquisar();
            logger.info("Quantidade encontrada: " + fornecedores.size());
            return fornecedores;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar Fornecedores: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}