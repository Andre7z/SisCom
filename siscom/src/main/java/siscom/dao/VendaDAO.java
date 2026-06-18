package siscom.dao;

import siscom.model.Venda;
import siscom.model.VendaProduto;
import siscom.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

    Connection conn = null;

    public boolean salvar(Venda venda) {
        try {
            conn = Conexao.getConnection();

            String sqlVenda = "INSERT INTO venda (data_venda, valor_total, cliente_id) VALUES (?, ?, ?)";
            PreparedStatement psVenda = conn.prepareStatement(sqlVenda, PreparedStatement.RETURN_GENERATED_KEYS);

            psVenda.setDate(1, java.sql.Date.valueOf(venda.getData_venda()));
            psVenda.setDouble(2, venda.getValor_total());
            psVenda.setInt(3, venda.getCliente().getId());

            int qtdeLinhas = psVenda.executeUpdate();

            ResultSet rs = psVenda.getGeneratedKeys(); //pega o id que o banco acabou de gerar
            int idVenda = 0; //incia a váriavel

            if (rs.next()) {
                idVenda = rs.getInt(1);
            }
            //guarda o id no idVenda

            rs.close();
            psVenda.close();

            // Venda Produto
            for (VendaProduto vp : venda.getprodutos()) {
                String sqlItem = "INSERT INTO venda_produto (venda_id, produto_id, quantidade, preco_unit) VALUES (?, ?, ?, ?)";
                PreparedStatement psItem = conn.prepareStatement(sqlItem);

                psItem.setInt(1, idVenda); // utiliza o id que o banco gerou
                psItem.setInt(2, vp.getProduto().getId());
                psItem.setInt(3, vp.getQuantidade());
                psItem.setDouble(4, vp.getPreco_unit());

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

            String sqlItens = "DELETE FROM venda_produto WHERE venda_id = ?";
            PreparedStatement psItens = conn.prepareStatement(sqlItens);
            psItens.setInt(1, id);
            psItens.executeUpdate();
            psItens.close();

            String sqlVenda = "DELETE FROM venda WHERE id = ?";
            PreparedStatement psVenda = conn.prepareStatement(sqlVenda);
            psVenda.setInt(1, id);

            int qtdeLinhas = psVenda.executeUpdate();
            psVenda.close();

            return qtdeLinhas > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Conexao.fecharConexao();
        }
    }

    public boolean alterar(Venda venda) {
        try {
            conn = Conexao.getConnection();

            String sqlVenda = "UPDATE venda SET data_venda = ?, valor_total = ?, cliente_id = ? WHERE id = ?";
            PreparedStatement psVenda = conn.prepareStatement(sqlVenda);

            psVenda.setDate(1, java.sql.Date.valueOf(venda.getData_venda()));
            psVenda.setDouble(2, venda.getValor_total());
            psVenda.setInt(3, venda.getCliente().getId());
            psVenda.setInt(4, venda.getId());

            int qtdeLinhas = psVenda.executeUpdate();
            psVenda.close();

            String deleteItens = "DELETE FROM venda_produto WHERE venda_id = ?";
            PreparedStatement psDelete = conn.prepareStatement(deleteItens);
            psDelete.setInt(1, venda.getId());
            psDelete.executeUpdate();
            psDelete.close();

            for (VendaProduto vp : venda.getprodutos()) {
                String sqlItem = "INSERT INTO venda_produto (venda_id, produto_id, quantidade, preco_unit) VALUES (?, ?, ?, ?)";
                PreparedStatement psItem = conn.prepareStatement(sqlItem);

                psItem.setInt(1, venda.getId());
                psItem.setInt(2, vp.getProduto().getId());
                psItem.setInt(3, vp.getQuantidade());
                psItem.setDouble(4, vp.getPreco_unit());

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

    public List<Venda> pesquisarTodos() {
        try {
            List<Venda> vendas = new ArrayList<>();

            conn = Conexao.getConnection();

            String sql = "SELECT * FROM venda";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Venda venda = new Venda();

                venda.setId(rs.getInt("id"));
                venda.setData_venda(rs.getDate("data_venda").toLocalDate());
                venda.setValor_total(rs.getDouble("valor_total"));

                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("cliente_id"));
                venda.setCliente(cliente);

                vendas.add(venda);
            }

            rs.close();
            ps.close();
            return vendas;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Conexao.fecharConexao();
        }
    }

    public int restrigirVendas(String cpf) {
        try {
            conn = Conexao.getConnection();

            String sql = "SELECT COUNT(*) FROM venda v " + //quantas vendas existem
                         "INNER JOIN cliente c ON v.cliente_id = c.id " + // liga venda com o cliente
                         "WHERE c.cpf = ? " + //filtra pelo CPF passado
                         "AND EXTRACT(MONTH FROM v.data_venda) = EXTRACT(MONTH FROM CURRENT_DATE) " + //pega somente do mês atual
                         "AND EXTRACT(YEAR FROM v.data_venda) = EXTRACT(YEAR FROM CURRENT_DATE)"; //garante que é desse ano

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, cpf);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int total = rs.getInt(1);
                rs.close();
                ps.close();
                return total; //retorna quantidade total de vendas naquele cpf
            }

            rs.close();
            ps.close();
            return 0;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            Conexao.fecharConexao();
        }
    }
}