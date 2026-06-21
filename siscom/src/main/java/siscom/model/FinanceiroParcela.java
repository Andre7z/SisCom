package siscom.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceiroParcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int nParcela;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private Double valorOriginal;
    private Double desconto;
    private Double acrescimo;
    private Double valorFinal;
    private int status;

    @ManyToOne
    @JoinColumn(name = "financeiro_id")
    private Financeiro financeiro;
}