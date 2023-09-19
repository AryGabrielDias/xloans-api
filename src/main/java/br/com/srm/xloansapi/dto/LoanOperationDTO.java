package br.com.srm.xloansapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanOperationDTO {
    private String operation;
    private Long personId;
    private Long loanId;
    private Integer installmentsNumber;
    private Double loanValue;
    private String paymentStatus;
    private String operationDate;
}
