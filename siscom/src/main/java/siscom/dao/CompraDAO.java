package siscom.dao;

import siscom.model.Compra;
import siscom.model.CompraProduto;
import siscom.model.Fornecedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CompraDAO {

    Connection conn = null;

public boolean salvar(Compra compra) {
    try {
        conn = Conexao.getConnection();

        // salva compra
        String sqlCompra = "INSERT INTO compra (data_compra, valor_total, fornecedor_id) VALUES (?, ?, ?)";
        PreparedStatement psCompra = conn.prepareStatement(sqlCompra, PreparedStatement.RETURN_GENERATED_KEYS);

        psCompra.setDate(1, java.sql.Date.valueOf(compra.getData_compra()));
        psCompra.setDouble(2, compra.getValor_total());
        psCompra.setInt(3, compra.getFornecedor().getId());

        int qtdeLinhas = psCompra.executeUpdate();

        // pega ID gerado
        ResultSet rs = psCompra.getGeneratedKeys();
        int idCompra = 0;

        if (rs.next()) {
            idCompra = rs.getInt(1);
        }

        rs.close();
        psCompra.close();

        // CompraProduto
        for (CompraProduto cp : compra.getProdutos()) {

            String sqlItem = "INSERT INTO compra_produto (compra_id, produto_id, quantidade, preco_unit) VALUES (?, ?, ?, ?)";
            PreparedStatement psItem = conn.prepareStatement(sqlItem);

            psItem.setInt(1, idCompra);
            psItem.setInt(2, cp.getProduto().getId());
            psItem.setInt(3, cp.getQuantidade());
            psItem.setDouble(4, cp.getPreco_unit());

            psItem.executeUpdate();
            psItem.close();
        }

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

            String sql = "DELETE FROM compra WHERE id = ?";
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

    public boolean alterar(Compra compra) {
        try {
            conn = Conexao.getConnection();

            String sql = "UPDATE compra SET data_compra = ?, valor_total = ?, fornecedor_id = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDate(1, java.sql.Date.valueOf(compra.getData_compra()));
            ps.setDouble(2, compra.getValor_total());
            ps.setInt(3, compra.getFornecedor().getId());
            ps.setInt(4, compra.getId());

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

    public List<Compra> pesquisarTodos() {
        try {
            List<Compra> compras = new ArrayList<>();

            conn = Conexao.getConnection();

            String sql = "SELECT * FROM compra";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Compra compra = new Compra();
                Fornecedor fornecedor = new Fornecedor();

                compra.setId(rs.getInt("id"));
                compra.setData_compra(rs.getDate("data_compra").toLocalDate());
                compra.setValor_total(rs.getDouble("valor_total"));

                fornecedor.setId(rs.getInt("fornecedor_id"));
                compra.setFornecedor(fornecedor);

                compras.add(compra);
            }

            rs.close();
            ps.close();

            return compras;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Conexao.fecharConexao();
        }
    }
}