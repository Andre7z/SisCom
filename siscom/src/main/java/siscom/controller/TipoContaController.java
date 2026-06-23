package siscom.controller;

import java.util.List;

import siscom.dao.TipoContaDAO;
import siscom.model.TipoConta;

public class TipoContaController {

    TipoContaDAO tipoContaDAO = new TipoContaDAO();

    public boolean salvar(TipoConta tipoConta) {
        return tipoContaDAO.salvar(tipoConta);
    }

    public boolean alterar(TipoConta tipoConta) {
        return tipoContaDAO.alterar(tipoConta);
    }

    public boolean excluir(int id) {
        return tipoContaDAO.excluir(id);
    }

    public TipoConta pesquisar(int id) {
        return tipoContaDAO.pesquisarPorId(id);
    }

    public List<TipoConta> pesquisarTodos() {
        return tipoContaDAO.pesquisar();
    }
}