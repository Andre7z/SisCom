package siscom.dao;

import siscom.model.Produto;
import siscom.model.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    Connection conn = null;

    public boolean salvar(Produto produto) {
        try {
            conn = Conexao.getConnection();

            String sql = "INSERT INTO produto (nome, preco_medio, qtde_estoque, categoria_id, valor_ultima_compra, valor_ultima_venda) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco_medio());
            ps.setDouble(3, produto.getQtde_estoque());
            ps.setInt(4, produto.getCategoria().getId());
            ps.setDouble(5, produto.getValor_ultima_compra());
            ps.setDouble(6, produto.getValor_ultima_venda());

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

            String sql = "DELETE FROM produto WHERE id = ?";
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

    public boolean alterar(Produto produto) {
        try {
            conn = Conexao.getConnection();

            String sql = "UPDATE produto SET nome=?, preco_medio=?, qtde_estoque=?, categoria_id=?, valor_ultima_compra=?, valor_ultima_venda=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco_medio());
            ps.setDouble(3, produto.getQtde_estoque());
            ps.setInt(4, produto.getCategoria().getId());
            ps.setDouble(5, produto.getValor_ultima_compra());
            ps.setDouble(6, produto.getValor_ultima_venda());
            ps.setInt(7, produto.getId());

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

    public Produto pesquisar(int id) {
        try {
            conn = Conexao.getConnection();

            String sql = "SELECT * FROM produto WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Produto produto = new Produto();

                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco_medio(rs.getDouble("preco_medio"));
                produto.setQtde_estoque(rs.getDouble("qtde_estoque"));
                produto.setValor_ultima_compra(rs.getDouble("valor_ultima_compra"));
                produto.setValor_ultima_venda(rs.getDouble("valor_ultima_venda"));

                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("categoria_id"));
                produto.setCategoria(categoria);

                rs.close();
                ps.close();
                return produto;
            }

            rs.close();
            ps.close();
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Conexao.fecharConexao();
        }
    }

    public List<Produto> pesquisarTodos() {
        try {
            List<Produto> produtos = new ArrayList<>();

            conn = Conexao.getConnection();

            String sql = "SELECT * FROM produto";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto();

                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco_medio(rs.getDouble("preco_medio"));
                produto.setQtde_estoque(rs.getDouble("qtde_estoque"));
                produto.setValor_ultima_compra(rs.getDouble("valor_ultima_compra"));
                produto.setValor_ultima_venda(rs.getDouble("valor_ultima_venda"));

                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("categoria_id"));
                produto.setCategoria(categoria);

                produtos.add(produto);
            }

            rs.close();
            ps.close();
            return produtos;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Conexao.fecharConexao();
        }
    }

}