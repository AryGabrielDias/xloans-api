package br.com.srm.xloansapi.service;

import br.com.srm.xloansapi.dto.PersonDTO;
import br.com.srm.xloansapi.dto.SavePersonDTO;
import br.com.srm.xloansapi.exceptions.BusinessRetireeRuleException;
import br.com.srm.xloansapi.exceptions.BusinessStudentRuleException;
import br.com.srm.xloansapi.exceptions.InvalidIdentificationException;

import java.util.List;

public interface PersonService {

    PersonDTO savePerson(SavePersonDTO savePersonDTO) throws BusinessStudentRuleException, BusinessRetireeRuleException, InvalidIdentificationException;

    PersonDTO updatePerson(SavePersonDTO updatePersonDTO) throws BusinessStudentRuleException, BusinessRetireeRuleException, InvalidIdentificationException;

    List<PersonDTO> getAllPersons();

    Long deletePerson(Long personId);

    PersonDTO getPersonById(Long personId);

}
