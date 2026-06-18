package siscom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import siscom.model.VendaProduto;
import siscom.model.Produto;
import siscom.model.Venda;

public class VendaProdutoDAO {

    Connection conn = null;

    public boolean salvar(VendaProduto produto) {
        try {
            conn = Conexao.getConnection();

            String sql = "INSERT INTO venda_produto (venda_id, produto_id, quantidade, preco_unit) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, produto.getVenda().getId());
            ps.setInt(2, produto.getProduto().getId());
            ps.setInt(3, produto.getQuantidade());
            ps.setDouble(4, produto.getPreco_unit());

            ps.executeUpdate();
            ps.close();

            System.out.println("produtoVenda salvo!");
            return true;

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

            String sql = "DELETE FROM venda_produto WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();
            ps.close();

            System.out.println("produtoVenda excluído!");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Conexao.fecharConexao();
        }
    }


    public boolean alterar(VendaProduto produto) {
        try {
            conn = Conexao.getConnection();

            String sql = "UPDATE venda_produto SET quantidade=?, preco_unit=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, produto.getQuantidade());
            ps.setDouble(2, produto.getPreco_unit());
            ps.setInt(3, produto.getId());

            ps.executeUpdate();
            ps.close();

            System.out.println("produtoVenda alterado!");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Conexao.fecharConexao();
        }
    }

    public VendaProduto pesquisar(int id) {
        VendaProduto produto = null;

        try {
            conn = Conexao.getConnection();

            String sql = "SELECT * FROM venda_produto WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                produto = new VendaProduto();

                produto.setId(rs.getInt("id"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setPreco_unit(rs.getDouble("preco_unit"));

                Produto p = new Produto();
                p.setId(rs.getInt("produto_id"));

                Venda v = new Venda();
                v.setId(rs.getInt("venda_id"));

                produto.setProduto(p);
                produto.setVenda(v);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao();
        }

        return produto;
    }

}