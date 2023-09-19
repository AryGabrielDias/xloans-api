package br.com.srm.xloansapi.service;

import br.com.srm.xloansapi.dto.SavePersonDTO;
import br.com.srm.xloansapi.exceptions.BusinessRetireeRuleException;
import br.com.srm.xloansapi.exceptions.BusinessStudentRuleException;
import br.com.srm.xloansapi.exceptions.InvalidIdentificationException;
import br.com.srm.xloansapi.model.Person;
import br.com.srm.xloansapi.repository.PersonRepository;
import br.com.srm.xloansapi.service.impl.PersonServiceImpl;
import br.com.srm.xloansapi.utils.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    private final Utils utils = new Utils();

    @Test
    public void should_returnPersonDTO_when_personIsSaved() throws BusinessStudentRuleException, BusinessRetireeRuleException, InvalidIdentificationException {
        var savePersonDTO = new SavePersonDTO();
        savePersonDTO.setId(1L);
        savePersonDTO.setName("Usuario PJ");
        savePersonDTO.setIdentification("10110110111111");
        savePersonDTO.setBirthDate("01/02/1979");

        var savedPerson = utils.saveDtoToEntity(savePersonDTO);

        when(personRepository.save(any(Person.class))).thenReturn(savedPerson);

        var personSucess = personService.savePerson(savePersonDTO);

        assertEquals(personSucess.getName(), savePersonDTO.getName());
        assertEquals(personSucess.getIdentification(), savePersonDTO.getIdentification());
    }

    @Test
    public void should_returnPersonDTO_when_personIsUpdated() throws BusinessStudentRuleException, BusinessRetireeRuleException, InvalidIdentificationException {
        var savePersonDTO = new SavePersonDTO();
        savePersonDTO.setId(1L);
        savePersonDTO.setName("Usuario PJ");
        savePersonDTO.setIdentification("10110110111122");
        savePersonDTO.setBirthDate("01/02/1979");

        var updatedPerson = utils.saveDtoToEntity(savePersonDTO);

        when(personRepository.saveAndFlush(any(Person.class))).thenReturn(updatedPerson);

        var personUp = personService.updatePerson(savePersonDTO);

        assertEquals(personUp.getName(), savePersonDTO.getName());
        assertEquals(personUp.getIdentification(), savePersonDTO.getIdentification());
    }

    @Test
    public void should_returnPersonDTOList_when_called() {
        when(personRepository.findAll()).thenReturn(new ArrayList<>());
        var personDTOList = personService.getAllPersons();
        assertTrue(Objects.nonNull(personDTOList));
    }

    @Test
    public void should_returnDeletedPersonId_when_personIsDeleted() {
        doNothing().when(personRepository).deleteById(anyLong());
        var deletedPersonId = personService.deletePerson(1L);
        assertTrue(Objects.nonNull(deletedPersonId));
    }

    @Test
    public void should_returnPersonDTO_when_called() {
        when(personRepository.findByPersonId(1L)).thenReturn(new Person());
        var personDTO = personService.getPersonById(1L);
        assertTrue(Objects.nonNull(personDTO));
    }

    @Test
    public void should_throwInvalidIdentificationException_when_personIsSaved() {
        var savePersonDTO = new SavePersonDTO();
        savePersonDTO.setId(1L);
        savePersonDTO.setName("Usuario PJ");
        savePersonDTO.setIdentification("1011011011111");
        savePersonDTO.setBirthDate("01/02/1979");

        assertThrows(InvalidIdentificationException.class, () ->
                personService.savePerson(savePersonDTO));
    }

    @Test
    public void should_throwInvalidIdentificationException_when_personIsUpdated() {
        var updatePersonDTO = new SavePersonDTO();
        updatePersonDTO.setId(1L);
        updatePersonDTO.setName("Usuario PJ");
        updatePersonDTO.setIdentification("1011011011111");
        updatePersonDTO.setBirthDate("01/02/1979");

        assertThrows(InvalidIdentificationException.class, () ->
                personService.updatePerson(updatePersonDTO));
    }

    @Test
    public void should_throwBusinessStudentRuleException_when_personIsSaved() {
        var savePersonDTO = new SavePersonDTO();
        savePersonDTO.setId(1L);
        savePersonDTO.setName("Usuario EU");
        savePersonDTO.setIdentification("12341237");
        savePersonDTO.setBirthDate("01/02/1979");

        assertThrows(BusinessStudentRuleException.class, () ->
                personService.savePerson(savePersonDTO));
    }

    @Test
    public void should_throwBusinessStudentRuleException_when_personIsUpdated() {
        var updatePersonDTO = new SavePersonDTO();
        updatePersonDTO.setId(1L);
        updatePersonDTO.setName("Usuario EU");
        updatePersonDTO.setIdentification("12341237");
        updatePersonDTO.setBirthDate("01/02/1979");

        assertThrows(BusinessStudentRuleException.class, () ->
                personService.updatePerson(updatePersonDTO));
    }

    @Test
    public void should_throwBusinessRetireeRuleException_when_personIsSaved() {
        var savePersonDTO = new SavePersonDTO();
        savePersonDTO.setId(1L);
        savePersonDTO.setName("Usuario AP");
        savePersonDTO.setIdentification("1234512345");
        savePersonDTO.setBirthDate("01/02/1979");

        assertThrows(BusinessRetireeRuleException.class, () ->
                personService.savePerson(savePersonDTO));
    }

    @Test
    public void should_throwBusinessRetireeRuleException_when_personIsUpdated() {
        var updatePersonDTO = new SavePersonDTO();
        updatePersonDTO.setId(1L);
        updatePersonDTO.setName("Usuario AP");
        updatePersonDTO.setIdentification("1234512345");
        updatePersonDTO.setBirthDate("01/02/1979");

        assertThrows(BusinessRetireeRuleException.class, () ->
                personService.updatePerson(updatePersonDTO));
    }

}
