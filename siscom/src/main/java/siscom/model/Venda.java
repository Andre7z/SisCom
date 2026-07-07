package siscom.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Cliente cliente;

    private LocalDate dataVenda;
    private Double valorTotal;

    @OneToMany(mappedBy = "venda",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<VendaProduto> produtos;

    @OneToOne
    private Financeiro financeiro;
}
// private int id;
// private LocalDate data_venda;
// private double valor_total;
// private List<VendaProduto> produtos;
// private Cliente cliente;



// public int getId() {
//     return id;
// }
// public void setId(int id) {
//     this.id = id;
// }
// public LocalDate getData_venda() {
//     return data_venda;
// }
// public void setData_venda(LocalDate data_venda) {
//     this.data_venda = data_venda;
// }
// public double getValor_total() {
//     return valor_total;
// }
// public void setValor_total(double valor_total) {
//     this.valor_total = valor_total;
// }
// public List<VendaProduto> getprodutos() {
//     return produtos;
// }
// public void setprodutos(List<VendaProduto> produtos) {
//     this.produtos = produtos;
// }
// public Cliente getCliente() {
//     return cliente;
// }
// public void setCliente(Cliente cliente) {
//     this.cliente = cliente;
// }

// public Venda() {}
// public Venda(int id, LocalDate data_venda, double valor_total, Cliente cliente) {
//     this.id = id;
//     this.data_venda = data_venda;
//     this.valor_total = valor_total;
//     this.cliente = cliente;
// }
