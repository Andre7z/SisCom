package siscom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import siscom.model.CompraProduto;
import siscom.model.Produto;
import siscom.model.Compra;

public class CompraProdutoDAO {

    Connection conn = null;

    public boolean salvar(CompraProduto cp) {
        try {
            conn = Conexao.getConnection();

            String sql = "INSERT INTO compra_produto (compra_id, produto_id, quantidade, preco_unit) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, cp.getCompra().getId());
            ps.setInt(2, cp.getProduto().getId());
            ps.setInt(3, cp.getQuantidade());
            ps.setDouble(4, cp.getPreco_unit());

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

            String sql = "DELETE FROM compra_produto WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();
            ps.close();

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

    public boolean alterar(CompraProduto cp) {
        try {
            conn = Conexao.getConnection();

            String sql = "UPDATE compra_produto SET quantidade=?, preco_unit=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, cp.getQuantidade());
            ps.setDouble(2, cp.getPreco_unit());
            ps.setInt(3, cp.getId());

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

    public List<CompraProduto> pesquisarTodos() {
        try {
            List<CompraProduto> cps = new ArrayList<>();

            conn = Conexao.getConnection();

            String sql = "SELECT * FROM compra_produto WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CompraProduto cp = new CompraProduto();

                cp.setId(rs.getInt("id"));
                cp.setQuantidade(rs.getInt("quantidade"));
                cp.setPreco_unit(rs.getDouble("preco_unit"));

                Produto p = new Produto();
                p.setId(rs.getInt("produto_id"));

                Compra c = new Compra();
                c.setId(rs.getInt("compra_id"));

                cp.setProduto(p);
                cp.setCompra(c);
            }

            rs.close();
            ps.close();
            return cps;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Conexao.fecharConexao();
        }
    }
}
