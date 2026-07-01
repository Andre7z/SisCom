package siscom.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate dataCompra;
    private double valorTotal;

    @ManyToOne
    private Fornecedor fornecedor;

    @OneToMany(mappedBy = "compra",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<CompraProduto> produtos;

    @OneToOne
    private Financeiro financeiro;
}

// private int id;
// private LocalDate data_compra;
// private double valor_total;
// private Fornecedor fornecedor;
// private List<CompraProduto> produtos;
// public int getId() {
//     return id;
// }
// public void setId(int id) {
//     this.id = id;
// }
// public LocalDate getData_compra() {
//     return data_compra;
// }
// public void setData_compra(LocalDate data_compra) {
//     this.data_compra = data_compra;
// }
// public double getValor_total() {
//     return valor_total;
// }
// public void setValor_total(double valor_total) {
//     this.valor_total = valor_total;
// }
// public Fornecedor getFornecedor() {
//     return fornecedor;
// }
// public void setFornecedor(Fornecedor fornecedor) {
//     this.fornecedor = fornecedor;
// }
// public List<CompraProduto> getProdutos() {
//     return produtos;
// }
// public void setProdutos(List<CompraProduto> produtos) {
//     this.produtos = produtos;
// }

// public Compra(){}
// public Compra(int id, LocalDate data_compra, double valor_total, Fornecedor fornecedor) {
//     this.id = id;
//     this.data_compra = data_compra;
//     this.valor_total = valor_total;
//     this.fornecedor = fornecedor;
// }
