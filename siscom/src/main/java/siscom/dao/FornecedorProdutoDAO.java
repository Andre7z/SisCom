package siscom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import siscom.model.FornecedorProduto;

public class FornecedorProdutoDAO {

    Connection conn = null;

    public boolean salvar(FornecedorProduto fp) {
        try {
            conn = Conexao.getConnection();

            String sql = "INSERT INTO fornecedor_produto (fornecedor_id, produto_id) VALUES (?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, fp.getFornecedor().getId());
            ps.setInt(2, fp.getProduto().getId());

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

    public boolean excluir(int id){
        try{
            conn = Conexao.getConnection();

            String sql = "DELETE FROM fornecedor_produto WHERE ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            int qtdeLinhas = ps.executeUpdate();
            ps.close();
            return qtdeLinhas > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;}
            finally {
                Conexao.fecharConexao();
            }
        }
            public boolean alterar(FornecedorProduto fn) {
        try {
            conn = Conexao.getConnection();

            String sql = "UPDATE fornecedor_produto SET nome=? WHERE ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, fn.getFornecedor().getId());
            ps.setInt(2,fn.getProduto().getId());

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

    public List<FornecedorProduto> pesquisarTodos() {
        try {
            List<FornecedorProduto> FornecedorProdutos = new ArrayList<>();

            conn = Conexao.getConnection();

            String sql = "SELECT * FROM fornecedor_produto";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                FornecedorProduto fn = new FornecedorProduto();

                ps.setInt(1, fn.getFornecedor().getId());
                ps.setInt(2,fn.getProduto().getId());

                FornecedorProdutos.add(fn);
            }

            rs.close();
            ps.close();
            return FornecedorProdutos;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Conexao.fecharConexao();
        }
    }
}