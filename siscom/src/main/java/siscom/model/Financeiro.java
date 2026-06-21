package siscom.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Financeiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate dataConta;
    private int pagarOuReceber;

    @ManyToOne
    @JoinColumn(name = "tipo_conta_id")
    private TipoConta tipoConta;

    @ManyToOne
    @JoinColumn(name = "forma_pagamento_id")
    private FormaPagamento formaPagamento;

    @OneToMany(mappedBy = "financeiro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FinanceiroParcela> parcelas;
}