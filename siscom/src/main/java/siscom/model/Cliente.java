package siscom.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String nome;
    private String cpf;
    private String rg;
    private String endereco;
    private String telefone;

}


// private int id;
// private String nome;
// private String cpf;
// private String rg;
// private String endereco;
// private String telefone;

// public int getId() {
//     return id;
// }

// public void setId(int id) {
//     this.id = id;
// }

// public String getNome() {
//     return nome;
// }

// public void setNome(String nome) {
//     this.nome = nome;
// }

// public String getCpf() {
//     return cpf;
// }

// public void setCpf(String cpf) {
//     this.cpf = cpf;
// }

// public String getRg() {
//     return rg;
// }

// public void setRg(String rg) {
//     this.rg = rg;
// }

// public String getEndereco() {
//     return endereco;
// }

// public void setEndereco(String endereco) {
//     this.endereco = endereco;
// }

// public String getTelefone() {
//     return telefone;
// }

// public void setTelefone(String telefone) {
//     this.telefone = telefone;
// }

// public Cliente() {}

// public Cliente(int id, String nome, String cpf, String rg, String endereco, String telefone) {
//     this.id = id;
//     this.nome = nome;
//     this.cpf = cpf;
//     this.rg = rg;
//     this.endereco = endereco;
//     this.telefone = telefone;
// }