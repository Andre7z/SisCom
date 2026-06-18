package siscom.dao;

import siscom.model.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    Connection conn = null;

    public boolean salvar(Categoria categoria) {
        try {
            conn = Conexao.getConnection();

            String sql = "INSERT INTO CATEGORIA (nome) VALUES (?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, categoria.getNome());

            int qtdeLinhas = ps.executeUpdate();
            ps.close();
            return qtdeLinhas > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Conexao.fecharConexao();
        }
    }

    public boolean excluir(int id) {
        try {
            conn = Conexao.getConnection();

            String sql = "DELETE FROM categoria WHERE ID = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            int qtdeLinhas = ps.executeUpdate();
            ps.close();
            return qtdeLinhas > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Conexao.fecharConexao();
        }
    }

    public boolean alterar(Categoria categoria) {
        try {
            conn = Conexao.getConnection();

            String sql = "UPDATE categoria SET nome=? WHERE ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, categoria.getNome());
            ps.setInt(2,categoria.getId());

            int qtdeLinhas = ps.executeUpdate();
            ps.close();

            return qtdeLinhas > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Conexao.fecharConexao();
        }
    }

    public List<Categoria> pesquisarTodos() {
        try {
            List<Categoria> categorias = new ArrayList<>();

            conn = Conexao.getConnection();

            String sql = "SELECT * FROM categoria";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Categoria categoria = new Categoria();

                categoria.setId(rs.getInt("id"));
                categoria.setNome(rs.getString("nome"));

                categorias.add(categoria);
            }

            rs.close();
            ps.close();
            return categorias;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Conexao.fecharConexao();
        }
    }
}
