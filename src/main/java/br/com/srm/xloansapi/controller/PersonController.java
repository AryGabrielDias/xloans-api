package br.com.srm.xloansapi.controller;

import br.com.srm.xloansapi.dto.PersonDTO;
import br.com.srm.xloansapi.dto.SavePersonDTO;
import br.com.srm.xloansapi.exceptions.BusinessRetireeRuleException;
import br.com.srm.xloansapi.exceptions.BusinessStudentRuleException;
import br.com.srm.xloansapi.exceptions.InvalidIdentificationException;
import br.com.srm.xloansapi.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v0/api/person")
public class PersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<PersonDTO> savePerson(@RequestBody SavePersonDTO savePersonDTO) throws BusinessStudentRuleException, BusinessRetireeRuleException, InvalidIdentificationException {
        return new ResponseEntity<>(personService.savePerson(savePersonDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PersonDTO> updatePerson(@RequestBody SavePersonDTO savePersonDTO) throws BusinessStudentRuleException, BusinessRetireeRuleException, InvalidIdentificationException {
        return new ResponseEntity<>(personService.updatePerson(savePersonDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        return new ResponseEntity<>(personService.getAllPersons(), HttpStatus.OK);
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<Long> deletePerson(@PathVariable("personId") Long personId) {
        return new ResponseEntity<>(personService.deletePerson(personId), HttpStatus.OK);
    }

    @GetMapping("/{personId}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable("personId") Long personId) {
        return new ResponseEntity<>(personService.getPersonById(personId), HttpStatus.OK);
    }

}
