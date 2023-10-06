package br.com.srm.xloansapi.service;

import br.com.srm.xloansapi.dto.LoanOperationDTO;
import br.com.srm.xloansapi.dto.SaveLoanDTO;
import br.com.srm.xloansapi.exceptions.*;

public interface LoanService {

    LoanOperationDTO saveLoan(SaveLoanDTO saveLoanDTO) throws UserNotFoundException, MaximalLoanValueException, InstallmentsNumberAboveException, MinimalMonthValueException, BusinessStudentRuleException, BusinessRetireeRuleException;

    LoanOperationDTO payLoan(Long loanId) throws LoanNotFoundException;
}
