package br.com.srm.xloansapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "pessoa", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
@Getter
@Setter
public class Person {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "nome", nullable = false, unique = true)
    private String name;

    @Column(name = "identificador", nullable = false, unique = true)
    private String identification;

    @Column(name = "data_nascimento", nullable = false, unique = true)
    private LocalDateTime birthDate;

    @Column(name = "tipo_identificador", nullable = false, unique = true)
    private String identifierType;

    @Column(name = "valor_min_mensal", nullable = false, unique = true)
    private Double minimalMonthValue;

    @Column(name = "valor_max_emprestimo", nullable = false, unique = true)
    private Double maximalLoanValue;

    @OneToMany(mappedBy = "pessoa")
    private Set<Loan> loans;


}
