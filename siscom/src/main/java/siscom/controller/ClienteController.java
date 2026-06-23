package siscom.controller;

import java.util.List;

import siscom.dao.ClienteDAO;
import siscom.model.Cliente;

public class ClienteController {

    ClienteDAO clienteDAO = new ClienteDAO();

    public boolean salvar(Cliente cliente) {
        return clienteDAO.salvar(cliente);
    }

    public boolean alterar(Cliente cliente) {
        return clienteDAO.alterar(cliente);
    }

    public boolean excluir(int id) {
        return clienteDAO.excluir(id);
    }

    public Cliente pesquisar(int id) {
        return clienteDAO.pesquisarPorId(id);
    }

    public List<Cliente> pesquisarTodos() {
        return clienteDAO.pesquisar();
    }
}