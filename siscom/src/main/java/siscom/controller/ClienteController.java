package siscom.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import siscom.dao.ClienteDAO;
import siscom.model.Cliente;

public class ClienteController {

    private static final Logger logger =
            LogManager.getLogger(ClienteController.class);

    private ClienteDAO clienteDAO = new ClienteDAO();

    public boolean salvar(Cliente cliente) {
        logger.info("Iniciando salvar Cliente");

        try {
            if (cliente == null) {
                logger.error("Cliente nulo");
                return false;
            }

            if (cliente.getNome() == null ||
                    cliente.getNome().isBlank()) {
                logger.error("Nome inválido");
                return false;
            }

            if (cliente.getCpf() == null ||
                    cliente.getCpf().isBlank()) {
                logger.error("CPF inválido");
                return false;
            }

            boolean resultado = clienteDAO.salvar(cliente);

            if (resultado) {
                logger.info("Cliente salvo com sucesso");
            } else {
                logger.error("Falha ao salvar Cliente");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao salvar Cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Cliente cliente) {
        logger.info("Iniciando alterar Cliente");

        try {
            boolean resultado = clienteDAO.alterar(cliente);

            if (resultado) {
                logger.info("Cliente alterado com sucesso");
            } else {
                logger.error("Falha ao alterar Cliente");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao alterar Cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        logger.info("Iniciando excluir Cliente id=" + id);

        try {
            boolean resultado = clienteDAO.excluir(id);

            if (resultado) {
                logger.info("Cliente excluído com sucesso");
            } else {
                logger.error("Falha ao excluir Cliente");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao excluir Cliente: " + e.getMessage());
            return false;
        }
    }

    public Cliente pesquisar(int id) {
        logger.info("Iniciando pesquisar Cliente id=" + id);

        try {
            Cliente cliente = clienteDAO.pesquisarPorId(id);

            if (cliente != null) {
                logger.info("Cliente encontrado");
            } else {
                logger.error("Cliente não encontrado");
            }

            return cliente;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar Cliente: " + e.getMessage());
            return null;
        }
    }

    public List<Cliente> pesquisarTodos() {
        logger.info("Iniciando pesquisar todos os Clientes");

        try {
            List<Cliente> clientes = clienteDAO.pesquisar();
            logger.info("Quantidade encontrada: " + clientes.size());
            return clientes;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar Clientes: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}