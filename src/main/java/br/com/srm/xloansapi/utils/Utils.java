package br.com.srm.xloansapi.utils;

import br.com.srm.xloansapi.dto.LoanOperationDTO;
import br.com.srm.xloansapi.dto.PersonDTO;
import br.com.srm.xloansapi.dto.SaveLoanDTO;
import br.com.srm.xloansapi.dto.SavePersonDTO;
import br.com.srm.xloansapi.model.Loan;
import br.com.srm.xloansapi.model.Person;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
public class Utils {

    public Person saveDtoToEntity(SavePersonDTO savePersonDTO) {
        var person = new Person();
        person.setId(savePersonDTO.getId());
        person.setName(savePersonDTO.getName());
        person.setIdentification(savePersonDTO.getIdentification());
        person.setBirthDate(convertDateStringToLocalDateTime(savePersonDTO.getBirthDate()));
        person.setIdentifierType(defineIdentifierType((savePersonDTO.getIdentification().length())));
        person.setMinimalMonthValue(defineMinimalMonthValue((savePersonDTO.getIdentification().length())));
        person.setMaximalLoanValue(defineMaximalLoanValue((savePersonDTO.getIdentification().length())));
        return person;
    }

    public PersonDTO entityToDto(Person person) {
        var dto = new PersonDTO();
        dto.setId(person.getId());
        dto.setName(person.getName());
        dto.setIdentification(person.getIdentification());
        dto.setBirthDate(convertLocalDateTimeToString(person.getBirthDate()));
        dto.setIdentifierType(person.getIdentifierType());
        dto.setMinimalMonthValue(person.getMinimalMonthValue());
        dto.setMaximalLoanValue(person.getMaximalLoanValue());
        return dto;
    }

    public Loan saveDtoToEntity(SaveLoanDTO saveLoanDTO, Person person) {
        var loan = new Loan();
        loan.setId(saveLoanDTO.getId());
        loan.setPerson(person);
        loan.setLoanValue(saveLoanDTO.getLoanValue());
        loan.setInstallmentsNumber(saveLoanDTO.getInstallmentsNumber());
        loan.setPaymentStatus("vigente");
        loan.setCreationDate(LocalDateTime.now());
        return loan;
    }

    public LoanOperationDTO entityToOperationDto(Loan loan, String operationType) {
        var operation = new LoanOperationDTO();
        operation.setOperation(operationType.equals("c") ? "contract - sucess" : "payment - sucess");
        operation.setPersonId(loan.getPerson().getId());
        operation.setLoanId(loan.getId());
        operation.setInstallmentsNumber(loan.getInstallmentsNumber());
        operation.setLoanValue(loan.getLoanValue());
        operation.setPaymentStatus(loan.getPaymentStatus());
        operation.setOperationDate(convertLocalDateTimeToString(loan.getCreationDate()));
        return operation;
    }

    public String defineIdentifierType(int charQuantity) {
        if (charQuantity == 8) {
            return "EU";
        }
        else if (charQuantity == 10) {
            return "AP";
        }
        else if (charQuantity == 11) {
            return "PF";
        }
        else if (charQuantity == 14) {
            return "PJ";
        }
        else {
            return "NI";
        }
    }

    public Double defineMinimalMonthValue(int charQuantity) {
        if (charQuantity == 8) {
            return 100.00;
        }
        else if (charQuantity == 10) {
            return 400.00;
        }
        else if (charQuantity == 11) {
            return 300.00;
        }
        else if (charQuantity == 14) {
            return 1000.00;
        }
        else {
            return 0.00;
        }
    }

    public Double defineMaximalLoanValue(int charQuantity) {
        if (charQuantity == 8) {
            return 10000.00;
        }
        else if (charQuantity == 10) {
            return 25000.00;
        }
        else if (charQuantity == 11) {
            return 10000.00;
        }
        else if (charQuantity == 14) {
            return 100000.00;
        }
        else {
            return 0.00;
        }
    }

    public String convertLocalDateTimeToString(LocalDateTime dateTime) {
        var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(dateTimeFormatter);
    }

    public
    LocalDateTime convertDateStringToLocalDateTime(String date) {
        var parser = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, parser).atStartOfDay();
    }

    public Double calculateInstallments(Integer installmentsNumber, Double loanValue) {
        return loanValue / installmentsNumber;
    }

    public boolean isValidIdentification(int length) {
        return length == 8 || length == 10 || length == 11 || length == 14;
    }

    public boolean validateStudentRule(String identification) {
        var firstDigit = Integer.valueOf(identification.indexOf(0));
        var lastDigit = Integer.valueOf(identification.indexOf(identification.length() - 1));
        var sum = firstDigit + lastDigit;
        return sum == 9;
    }

    public boolean validateRetireeRule(String identification) {
        var lastDigit = identification.substring(identification.length() - 1);
        var str = identification.substring(0, identification.length() - 1);
        return str.contains(lastDigit);
    }

    public boolean isUniversityStudent(int length) {
        return length == 8;
    }

    public boolean isRetiree(int length) {
        return length == 10;
    }
}
