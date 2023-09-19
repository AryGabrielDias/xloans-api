package br.com.srm.xloansapi.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "emprestimo", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
@Data
public class Loan {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_pessoa", nullable = false)
    private Person person;

    @Column(name = "valor_emprestimo", nullable = false, unique = true)
    private Double loanValue;

    @Column(name = "numero_parcelas", nullable = false, unique = true)
    private Integer installmentsNumber;

    @Column(name = "status_pagamento", nullable = false, unique = true)
    private String paymentStatus;

    @Column(name = "data_criacao", nullable = false, unique = true)
    private LocalDateTime creationDate;
}
