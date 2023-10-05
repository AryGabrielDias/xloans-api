package br.com.srm.xloansapi.service.impl;

import br.com.srm.xloansapi.dto.ErrorHandlerDTO;
import br.com.srm.xloansapi.dto.LoanOperationDTO;
import br.com.srm.xloansapi.dto.SaveLoanDTO;
import br.com.srm.xloansapi.exceptions.*;
import br.com.srm.xloansapi.model.Loan;
import br.com.srm.xloansapi.repository.LoanRepository;
import br.com.srm.xloansapi.repository.PersonRepository;
import br.com.srm.xloansapi.service.LoanService;
import br.com.srm.xloansapi.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class LoanServiceImpl implements LoanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanServiceImpl.class);

    private final LoanRepository loanRepository;

    private final PersonRepository personRepository;

    private final Utils utils = new Utils();

    @Autowired

    public LoanServiceImpl(LoanRepository loanRepository, PersonRepository personRepository) {
        this.loanRepository = loanRepository;
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public LoanOperationDTO saveLoan(SaveLoanDTO saveLoanDTO) throws UserNotFoundException, MaximalLoanValueException, InstallmentsNumberAboveException, MinimalMonthValueException {

        var operationDTO = new LoanOperationDTO();

        var person = personRepository.findByPersonId(saveLoanDTO.getPersonId());

        if (Objects.isNull(person)) {
            throw new UserNotFoundException();
        }
        else {
            if (saveLoanDTO.getLoanValue() > person.getMaximalLoanValue()) {
                throw new MaximalLoanValueException();
            }
            else if (saveLoanDTO.getInstallmentsNumber() > 24) {
                throw new InstallmentsNumberAboveException();
            }
            else if (utils.calculateInstallments(saveLoanDTO.getInstallmentsNumber(), saveLoanDTO.getLoanValue()) <
                    person.getMinimalMonthValue()) {
                throw new MinimalMonthValueException();
            }
            else {
                var loan = utils.saveDtoToEntity(saveLoanDTO, person);
                operationDTO = utils.entityToOperationDto(loanRepository.save(loan), "c");
            }
        }

        return operationDTO;
    }

    @Override
    @Transactional
    public LoanOperationDTO payLoan(Long loanId) throws LoanNotFoundException {

        var operationDTO = new LoanOperationDTO();

        var loan = loanRepository.findByLoanId(loanId);

        if (Objects.isNull(loan)) {
            throw new LoanNotFoundException();
        }
        else {
            var payedLoan = new Loan();
            payedLoan.setId(loan.getId());
            payedLoan.setPerson(loan.getPerson());
            payedLoan.setLoanValue(loan.getLoanValue());
            payedLoan.setInstallmentsNumber(loan.getInstallmentsNumber());
            payedLoan.setPaymentStatus("pago");
            payedLoan.setCreationDate(loan.getCreationDate());

            operationDTO = utils.entityToOperationDto(
                    loanRepository.saveAndFlush(payedLoan), "p");
        }

        return operationDTO;
    }
}
