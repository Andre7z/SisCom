package siscom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class CompraProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "compra_id", nullable = false)
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    private int quantidade;
    private Double valorUnitario;

    
}

// private int id;
// private Produto produto;
// private Compra compra;
// private int quantidade;
// private double preco_unit;
// public int getId() {
//     return id;
// }
// public void setId(int id) {
//     this.id = id;
// }
// public Produto getProduto() {
//     return produto;
// }
// public void setProduto(Produto produto) {
//     this.produto = produto;
// }
// public Compra getCompra() {
//     return compra;
// }
// public void setCompra(Compra compra) {
//     this.compra = compra;
// }
// public int getQuantidade() {
//     return quantidade;
// }
// public void setQuantidade(int quantidade) {
//     this.quantidade = quantidade;
// }
// public double getPreco_unit() {
//     return preco_unit;
// }
// public void setPreco_unit(double preco_unit) {
//     this.preco_unit = preco_unit;
// }

// public CompraProduto(){}
// public CompraProduto(int id, Produto produto, Compra compra, int quantidade, double preco_unit) {
//     this.id = id;
//     this.produto = produto;
//     this.compra = compra;
//     this.quantidade = quantidade;
//     this.preco_unit = preco_unit;
// }