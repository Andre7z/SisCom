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
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @Override
    public String toString() {
        return nome;
    }
}

// private int id;
// private String nome;
// public int getId() {
// return id;
// }
// public void setId(int id) {
// this.id = id;
// }
// public String getNome() {
// return nome;
// }
// public void setNome(String nome) {
// this.nome = nome;
// }

// public Categoria(){}
// public Categoria(int id, String nome) {
// this.id = id;
// this.nome = nome;
// }