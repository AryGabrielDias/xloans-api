package br.com.srm.xloansapi.service.impl;

import br.com.srm.xloansapi.dto.PersonDTO;
import br.com.srm.xloansapi.dto.SavePersonDTO;
import br.com.srm.xloansapi.exceptions.BusinessRetireeRuleException;
import br.com.srm.xloansapi.exceptions.BusinessStudentRuleException;
import br.com.srm.xloansapi.exceptions.InvalidIdentificationException;
import br.com.srm.xloansapi.model.Person;
import br.com.srm.xloansapi.repository.PersonRepository;
import br.com.srm.xloansapi.service.PersonService;
import br.com.srm.xloansapi.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);

    private final PersonRepository personRepository;

    private final Utils utils = new Utils();

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public PersonDTO savePerson(SavePersonDTO savePersonDTO) throws BusinessStudentRuleException, BusinessRetireeRuleException, InvalidIdentificationException {

        var personDto = new PersonDTO();

        if (utils.isValidIdentification(
                savePersonDTO.getIdentification().length())) {

            this.validateRules(savePersonDTO.getIdentification());

            var person = utils.saveDtoToEntity(savePersonDTO);

            personDto = utils.entityToDto(personRepository.save(person));
        }
        else {
            throw new InvalidIdentificationException();
        }

        return personDto;
    }

    @Override
    @Transactional
    public PersonDTO updatePerson(SavePersonDTO updatePersonDTO) throws BusinessStudentRuleException, BusinessRetireeRuleException, InvalidIdentificationException {
        var personDto = new PersonDTO();

        if (utils.isValidIdentification(
                updatePersonDTO.getIdentification().length())) {

            this.validateRules(updatePersonDTO.getIdentification());

            var person = utils.saveDtoToEntity(updatePersonDTO);
            personDto = utils.entityToDto(personRepository.saveAndFlush(person));

        }
        else {
            throw new InvalidIdentificationException();
        }

        return personDto;
    }

    @Override
    @Transactional
    public List<PersonDTO> getAllPersons() {

        var personsDtoList = new ArrayList<PersonDTO>();

        try {
            var personsList = personRepository.findAll();

            for (Person person : personsList) {
                PersonDTO personDTO = utils.entityToDto(person);
                personsDtoList.add(personDTO);
            }
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return personsDtoList;
    }

    @Override
    @Transactional
    public Long deletePerson(Long personId) {

        try {
            personRepository.deleteById(personId);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return personId;
    }

    @Override
    @Transactional
    public PersonDTO getPersonById(Long personId) {

        var personDto = new PersonDTO();

        try {
            var person = personRepository.findByPersonId(personId);
            personDto = utils.entityToDto(person);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return personDto;
    }

    private void validateRules(String identification) throws BusinessStudentRuleException, BusinessRetireeRuleException {
        if (utils.isUniversityStudent(identification.length())) {
            if (!utils.validateStudentRule(identification)) {
                throw new BusinessStudentRuleException();
            }
        }

        if (utils.isRetiree(identification.length())) {
            if (utils.validateRetireeRule(identification)) {
                throw new BusinessRetireeRuleException();
            }
        }
    }

}
