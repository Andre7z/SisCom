package siscom.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import siscom.dao.CategoriaDAO;
import siscom.model.Categoria;

public class CategoriaController {

    private static final Logger logger =
            LogManager.getLogger(CategoriaController.class);

    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    public boolean salvar(Categoria categoria) {
        logger.info("Iniciando salvar Categoria");

        try {
            if (categoria == null) {
                logger.error("Categoria nula");
                return false;
            }

            if (categoria.getNome() == null ||
                    categoria.getNome().isBlank()) {
                logger.error("Nome da categoria inválido");
                return false;
            }

            boolean resultado = categoriaDAO.salvar(categoria);

            if (resultado) {
                logger.info("Categoria salva com sucesso");
            } else {
                logger.error("Falha ao salvar Categoria");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao salvar Categoria: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Categoria categoria) {
        logger.info("Iniciando alterar Categoria");

        try {
            boolean resultado = categoriaDAO.alterar(categoria);

            if (resultado) {
                logger.info("Categoria alterada com sucesso");
            } else {
                logger.error("Falha ao alterar Categoria");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao alterar Categoria: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        logger.info("Iniciando excluir Categoria id=" + id);

        try {
            boolean resultado = categoriaDAO.excluir(id);

            if (resultado) {
                logger.info("Categoria excluída com sucesso");
            } else {
                logger.error("Falha ao excluir Categoria");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao excluir Categoria: " + e.getMessage());
            return false;
        }
    }

    public Categoria pesquisar(int id) {
        logger.info("Iniciando pesquisar Categoria id=" + id);

        try {
            Categoria categoria = categoriaDAO.pesquisarPorId(id);

            if (categoria != null) {
                logger.info("Categoria encontrada");
            } else {
                logger.error("Categoria não encontrada");
            }

            return categoria;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar Categoria: " + e.getMessage());
            return null;
        }
    }

    public List<Categoria> pesquisarTodos() {
        logger.info("Iniciando pesquisar todas as Categorias");

        try {
            List<Categoria> categorias = categoriaDAO.pesquisar();
            logger.info("Quantidade encontrada: " + categorias.size());
            return categorias;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar Categorias: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}