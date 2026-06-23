package siscom.controller;

import java.util.List;

import siscom.dao.CategoriaDAO;
import siscom.model.Categoria;

public class CategoriaController {

    CategoriaDAO categoriaDAO = new CategoriaDAO();

    public boolean salvar(Categoria categoria) {
        return categoriaDAO.salvar(categoria);
    }

    public boolean alterar(Categoria categoria) {
        return categoriaDAO.alterar(categoria);
    }

    public boolean excluir(int id) {
        return categoriaDAO.excluir(id);
    }

    public Categoria pesquisar(int id) {
        return categoriaDAO.pesquisarPorId(id);
    }

    public List<Categoria> pesquisarTodos() {
        return categoriaDAO.pesquisar();
    }
}