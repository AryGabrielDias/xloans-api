package br.com.srm.xloansapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveLoanDTO {
    private Long id;
    private Long personId;
    private Double loanValue;
    private Integer installmentsNumber;

}
