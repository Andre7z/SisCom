package siscom.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import siscom.dao.UsuarioDAO;
import siscom.model.Usuario;

public class UsuarioController {

    private static final Logger logger =
            LogManager.getLogger(UsuarioController.class);

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean salvar(Usuario usuario) {
        logger.info("Iniciando salvar Usuario");

        try {
            if (usuario == null) {
                logger.error("Usuario nulo");
                return false;
            }

            if (usuario.getNome() == null || usuario.getNome().isBlank()) {
                logger.error("Nome inválido");
                return false;
            }

            if (usuario.getLogin() == null || usuario.getLogin().isBlank()) {
                logger.error("Login inválido");
                return false;
            }

            if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
                logger.error("Senha inválida");
                return false;
            }

            if (usuario.getPerfil() == null || usuario.getPerfil().isBlank()) {
                logger.error("Perfil inválido");
                return false;
            }

            boolean resultado = usuarioDAO.salvar(usuario);

            if (resultado) {
                logger.info("Usuario salvo com sucesso");
            } else {
                logger.error("Falha ao salvar Usuario");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao salvar Usuario", e);
            return false;
        }
    }

    public boolean alterar(Usuario usuario) {
        logger.info("Iniciando alterar Usuario");

        try {
            boolean resultado = usuarioDAO.alterar(usuario);

            if (resultado) {
                logger.info("Usuario alterado com sucesso");
            } else {
                logger.error("Falha ao alterar Usuario");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao alterar Usuario", e);
            return false;
        }
    }

    public boolean excluir(int id) {
        logger.info("Iniciando excluir Usuario id=" + id);

        try {
            boolean resultado = usuarioDAO.excluir(id);

            if (resultado) {
                logger.info("Usuario excluido com sucesso");
            } else {
                logger.error("Falha ao excluir Usuario");
            }

            return resultado;

        } catch (Exception e) {
            logger.error("Erro ao excluir Usuario", e);
            return false;
        }
    }

    public Usuario pesquisar(int id) {
        logger.info("Iniciando pesquisar Usuario id=" + id);

        try {
            Usuario usuario = usuarioDAO.pesquisarPorId(id);

            if (usuario != null) {
                logger.info("Usuario encontrado");
            } else {
                logger.error("Usuario nao encontrado");
            }

            return usuario;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar Usuario", e);
            return null;
        }
    }

    public List<Usuario> pesquisarTodos() {
        logger.info("Iniciando pesquisar todos os Usuarios");

        try {
            List<Usuario> lista = usuarioDAO.pesquisar();
            logger.info("Quantidade encontrada: " + lista.size());
            return lista;

        } catch (Exception e) {
            logger.error("Erro ao pesquisar Usuarios", e);
            return new ArrayList<>();
        }
    }

    public Usuario autenticar(String login, String senha) {
        logger.info("Iniciando autenticacao");

        try {
            if (login == null || login.isBlank()) {
                logger.error("Login inválido");
                return null;
            }

            if (senha == null || senha.isBlank()) {
                logger.error("Senha inválida");
                return null;
            }

            Usuario usuario = usuarioDAO.autenticar(login, senha);

            if (usuario != null) {
                logger.info("Autenticacao realizada com sucesso");
            } else {
                logger.error("Login ou senha invalidos");
            }

            return usuario;

        } catch (Exception e) {
            logger.error("Erro ao autenticar Usuario", e);
            return null;
        }
    }
}