package siscom.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import siscom.model.Venda;

public class VendaDAO {

    public boolean salvar(Venda venda) {
        Transaction transaction = null;

        try (Session session = Conexao.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(venda);
            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    public boolean alterar(Venda venda) {
        Transaction transaction = null;

        try (Session session = Conexao.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(venda);
            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    public boolean excluir(int id) {
        Transaction transaction = null;

        try (Session session = Conexao.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Venda venda = session.get(Venda.class, id);

            if (venda == null) {
                transaction.rollback();
                return false;
            }

            session.remove(venda);
            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    public List<Venda> pesquisar() {
        try (Session session = Conexao.getSessionFactory().openSession()) {
            return session.createQuery("from Venda", Venda.class).list();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public Venda pesquisarPorId(int id) {
        try (Session session = Conexao.getSessionFactory().openSession()) {
            return session.get(Venda.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    public int contarVendasPorCpfMes(String cpf) {
        try (Session session = Conexao.getSessionFactory().openSession()) {

            LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
            LocalDate proximoMes = inicioMes.plusMonths(1);

            Long quantidade = session.createQuery(
                    "select count(v) from Venda v " +
                    "where v.cliente.cpf = :cpf " +
                    "and v.dataVenda >= :inicioMes " +
                    "and v.dataVenda < :proximoMes",
                    Long.class)
                    .setParameter("cpf", cpf)
                    .setParameter("inicioMes", inicioMes)
                    .setParameter("proximoMes", proximoMes)
                    .uniqueResult();

            return quantidade != null ? quantidade.intValue() : 0;

        } catch (Exception e) {
            return 0;
        }
    }
}
// public boolean salvar(Venda venda) {
//     try {
//         conn = Conexao.getConnection();

//         String sqlVenda = "INSERT INTO venda (data_venda, valor_total, cliente_id) VALUES (?, ?, ?)";
//         PreparedStatement psVenda = conn.prepareStatement(sqlVenda, PreparedStatement.RETURN_GENERATED_KEYS);

//         psVenda.setDate(1, java.sql.Date.valueOf(venda.getData_venda()));
//         psVenda.setDouble(2, venda.getValor_total());
//         psVenda.setInt(3, venda.getCliente().getId());

//         int qtdeLinhas = psVenda.executeUpdate();

//         ResultSet rs = psVenda.getGeneratedKeys(); //pega o id que o banco acabou de gerar
//         int idVenda = 0; //incia a váriavel

//         if (rs.next()) {
//             idVenda = rs.getInt(1);
//         }
//         //guarda o id no idVenda

//         rs.close();
//         psVenda.close();

//         // Venda Venda
//         for (VendaVenda vp : venda.getVendas()) {
//             String sqlItem = "INSERT INTO venda_Venda (venda_id, Venda_id, quantidade, preco_unit) VALUES (?, ?, ?, ?)";
//             PreparedStatement psItem = conn.prepareStatement(sqlItem);

//             psItem.setInt(1, idVenda); // utiliza o id que o banco gerou
//             psItem.setInt(2, vp.getVenda().getId());
//             psItem.setInt(3, vp.getQuantidade());
//             psItem.setDouble(4, vp.getPreco_unit());

//             psItem.executeUpdate();
//             psItem.close();
//         }

//         return qtdeLinhas > 0;

//     } catch (Exception e) {
//         e.printStackTrace();
//         return false;
//     } finally {
//         Conexao.fecharConexao();
//     }
// }

// public boolean excluir(int id) {
//     try {
//         conn = Conexao.getConnection();

//         String sqlItens = "DELETE FROM venda_Venda WHERE venda_id = ?";
//         PreparedStatement psItens = conn.prepareStatement(sqlItens);
//         psItens.setInt(1, id);
//         psItens.executeUpdate();
//         psItens.close();

//         String sqlVenda = "DELETE FROM venda WHERE id = ?";
//         PreparedStatement psVenda = conn.prepareStatement(sqlVenda);
//         psVenda.setInt(1, id);

//         int qtdeLinhas = psVenda.executeUpdate();
//         psVenda.close();

//         return qtdeLinhas > 0;

//     } catch (Exception e) {
//         e.printStackTrace();
//         return false;
//     } finally {
//         Conexao.fecharConexao();
//     }
// }

// public boolean alterar(Venda venda) {
//     try {
//         conn = Conexao.getConnection();

//         String sqlVenda = "UPDATE venda SET data_venda = ?, valor_total = ?, cliente_id = ? WHERE id = ?";
//         PreparedStatement psVenda = conn.prepareStatement(sqlVenda);

//         psVenda.setDate(1, java.sql.Date.valueOf(venda.getData_venda()));
//         psVenda.setDouble(2, venda.getValor_total());
//         psVenda.setInt(3, venda.getCliente().getId());
//         psVenda.setInt(4, venda.getId());

//         int qtdeLinhas = psVenda.executeUpdate();
//         psVenda.close();

//         String deleteItens = "DELETE FROM venda_Venda WHERE venda_id = ?";
//         PreparedStatement psDelete = conn.prepareStatement(deleteItens);
//         psDelete.setInt(1, venda.getId());
//         psDelete.executeUpdate();
//         psDelete.close();

//         for (VendaVenda vp : venda.getVendas()) {
//             String sqlItem = "INSERT INTO venda_Venda (venda_id, Venda_id, quantidade, preco_unit) VALUES (?, ?, ?, ?)";
//             PreparedStatement psItem = conn.prepareStatement(sqlItem);

//             psItem.setInt(1, venda.getId());
//             psItem.setInt(2, vp.getVenda().getId());
//             psItem.setInt(3, vp.getQuantidade());
//             psItem.setDouble(4, vp.getPreco_unit());

//             psItem.executeUpdate();
//             psItem.close();
//         }

//         return qtdeLinhas > 0;

//     } catch (Exception e) {
//         e.printStackTrace();
//         return false;
//     } finally {
//         Conexao.fecharConexao();
//     }
// }

// public List<Venda> pesquisarTodos() {
//     try {
//         List<Venda> vendas = new ArrayList<>();

//         conn = Conexao.getConnection();

//         String sql = "SELECT * FROM venda";
//         PreparedStatement ps = conn.prepareStatement(sql);

//         ResultSet rs = ps.executeQuery();

//         while (rs.next()) {
//             Venda venda = new Venda();

//             venda.setId(rs.getInt("id"));
//             venda.setData_venda(rs.getDate("data_venda").toLocalDate());
//             venda.setValor_total(rs.getDouble("valor_total"));

//             Cliente cliente = new Cliente();
//             cliente.setId(rs.getInt("cliente_id"));
//             venda.setCliente(cliente);

//             vendas.add(venda);
//         }

//         rs.close();
//         ps.close();
//         return vendas;

//     } catch (Exception e) {
//         e.printStackTrace();
//         return null;
//     } finally {
//         Conexao.fecharConexao();
//     }
// }

// public int restrigirVendas(String cpf) {
//     try {
//         conn = Conexao.getConnection();

//         String sql = "SELECT COUNT(*) FROM venda v " + //quantas vendas existem
//                      "INNER JOIN cliente c ON v.cliente_id = c.id " + // liga venda com o cliente
//                      "WHERE c.cpf = ? " + //filtra pelo CPF passado
//                      "AND EXTRACT(MONTH FROM v.data_venda) = EXTRACT(MONTH FROM CURRENT_DATE) " + //pega somente do mês atual
//                      "AND EXTRACT(YEAR FROM v.data_venda) = EXTRACT(YEAR FROM CURRENT_DATE)"; //garante que é desse ano

//         PreparedStatement ps = conn.prepareStatement(sql);

//         ps.setString(1, cpf);

//         ResultSet rs = ps.executeQuery();

//         if (rs.next()) {
//             int total = rs.getInt(1);
//             rs.close();
//             ps.close();
//             return total; //retorna quantidade total de vendas naquele cpf
//         }

//         rs.close();
//         ps.close();
//         return 0;

//     } catch (Exception e) {
//         e.printStackTrace();
//         return 0;
//     } finally {
//         Conexao.fecharConexao();
//     }
// }