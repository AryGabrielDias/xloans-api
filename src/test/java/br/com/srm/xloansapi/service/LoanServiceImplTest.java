package br.com.srm.xloansapi.service;

import br.com.srm.xloansapi.dto.SaveLoanDTO;
import br.com.srm.xloansapi.exceptions.*;
import br.com.srm.xloansapi.model.Loan;
import br.com.srm.xloansapi.model.Person;
import br.com.srm.xloansapi.repository.LoanRepository;
import br.com.srm.xloansapi.repository.PersonRepository;
import br.com.srm.xloansapi.service.impl.LoanServiceImpl;
import br.com.srm.xloansapi.utils.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class LoanServiceImplTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private LoanServiceImpl loanService;

    private final Utils utils = new Utils();

    @Test
    public void should_returnOperationDTO_when_saveLoanIsCalled() throws UserNotFoundException, MaximalLoanValueException, InstallmentsNumberAboveException, MinimalMonthValueException {
        var saveLoanDTO = new SaveLoanDTO();
        saveLoanDTO.setId(1L);
        saveLoanDTO.setPersonId(1L);
        saveLoanDTO.setInstallmentsNumber(20);
        saveLoanDTO.setLoanValue(25000.00);

        var person = new Person();
        person.setId(1L);
        person.setName("Usuario PJ");
        person.setIdentification("10110110111111");
        person.setBirthDate(LocalDateTime.now());
        person.setIdentifierType("PJ");
        person.setMinimalMonthValue(1000.00);
        person.setMaximalLoanValue(100000.00);

        var savedLoan = utils.saveDtoToEntity(saveLoanDTO, person);

        when(personRepository.findByPersonId(1L)).thenReturn(person);

        when(loanRepository.save(any(Loan.class))).thenReturn(savedLoan);

        var loanSucess = loanService.saveLoan(saveLoanDTO);

        assertEquals(loanSucess.getPersonId(), 1L);
        assertEquals(loanSucess.getOperation(), "contract - sucess");
        assertEquals(loanSucess.getLoanValue(), saveLoanDTO.getLoanValue());
    }

    @Test
    public void should_returnOperationDTO_when_payLoanIsCalled() throws LoanNotFoundException {

        var person = new Person();
        person.setId(1L);
        person.setName("Usuario PJ");
        person.setIdentification("10110110111111");
        person.setBirthDate(LocalDateTime.now());
        person.setIdentifierType("PJ");
        person.setMinimalMonthValue(1000.00);
        person.setMaximalLoanValue(100000.00);

        var loan = new Loan();
        loan.setId(1L);
        loan.setPerson(person);
        loan.setLoanValue(50000.00);
        loan.setInstallmentsNumber(20);
        loan.setPaymentStatus("vigente");
        loan.setCreationDate(LocalDateTime.now());

        var payedLoan = new Loan();
        payedLoan.setId(1L);
        payedLoan.setPerson(person);
        payedLoan.setLoanValue(50000.00);
        payedLoan.setInstallmentsNumber(20);
        payedLoan.setPaymentStatus("pago");
        payedLoan.setCreationDate(LocalDateTime.now());

        when(loanRepository.findByLoanId(1L)).thenReturn(loan);

        when(loanRepository.saveAndFlush(any(Loan.class))).thenReturn(payedLoan);

        var operationDTO = loanService.payLoan(1L);

        assertEquals(operationDTO.getPersonId(), 1L);
        assertEquals(operationDTO.getOperation(), "payment - sucess");
        assertEquals(operationDTO.getPaymentStatus(), "pago");
        assertEquals(operationDTO.getLoanValue(), payedLoan.getLoanValue());

    }

    @Test
    public void should_throwLoanNotFoundException_when_saveLoanIsCalled() {
        var saveLoanDTO = new SaveLoanDTO();
        assertThrows(UserNotFoundException.class, () ->
                loanService.saveLoan(saveLoanDTO));
    }

    @Test
    public void should_throwLoanNotFoundException_when_payLoanIsCalled() {
        assertThrows(LoanNotFoundException.class, () ->
                loanService.payLoan(null));
    }

    @Test
    public void should_throwMaximalLoanValueException_when_saveLoanIsCalled() {
        var saveLoanDTO = new SaveLoanDTO();
        saveLoanDTO.setId(1L);
        saveLoanDTO.setPersonId(1L);
        saveLoanDTO.setInstallmentsNumber(20);
        saveLoanDTO.setLoanValue(250000.00);

        var person = new Person();
        person.setId(1L);
        person.setName("Usuario PJ");
        person.setIdentification("10110110111111");
        person.setBirthDate(LocalDateTime.now());
        person.setIdentifierType("PJ");
        person.setMinimalMonthValue(1000.00);
        person.setMaximalLoanValue(100000.00);

        when(personRepository.findByPersonId(1L)).thenReturn(person);

        assertThrows(MaximalLoanValueException.class, () ->
                loanService.saveLoan(saveLoanDTO));
    }

    @Test
    public void should_throwInstallmentsNumberAboveException_when_saveLoanIsCalled() {
        var saveLoanDTO = new SaveLoanDTO();
        saveLoanDTO.setId(1L);
        saveLoanDTO.setPersonId(1L);
        saveLoanDTO.setInstallmentsNumber(25);
        saveLoanDTO.setLoanValue(25000.00);

        var person = new Person();
        person.setId(1L);
        person.setName("Usuario PJ");
        person.setIdentification("10110110111111");
        person.setBirthDate(LocalDateTime.now());
        person.setIdentifierType("PJ");
        person.setMinimalMonthValue(1000.00);
        person.setMaximalLoanValue(100000.00);

        when(personRepository.findByPersonId(1L)).thenReturn(person);

        assertThrows(InstallmentsNumberAboveException.class, () ->
                loanService.saveLoan(saveLoanDTO));
    }

    @Test
    public void should_throwMinimalMonthValueException_when_saveLoanIsCalled() {
        var saveLoanDTO = new SaveLoanDTO();
        saveLoanDTO.setId(1L);
        saveLoanDTO.setPersonId(1L);
        saveLoanDTO.setInstallmentsNumber(24);
        saveLoanDTO.setLoanValue(20000.00);

        var person = new Person();
        person.setId(1L);
        person.setName("Usuario PJ");
        person.setIdentification("10110110111111");
        person.setBirthDate(LocalDateTime.now());
        person.setIdentifierType("PJ");
        person.setMinimalMonthValue(1000.00);
        person.setMaximalLoanValue(100000.00);

        when(personRepository.findByPersonId(1L)).thenReturn(person);

        assertThrows(MinimalMonthValueException.class, () ->
                loanService.saveLoan(saveLoanDTO));
    }


}
