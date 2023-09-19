package br.com.srm.xloansapi.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    private Long id;
    private String name;
    private String identification;
    private String birthDate;
    private String identifierType;
    private Double minimalMonthValue;
    private Double maximalLoanValue;
}
