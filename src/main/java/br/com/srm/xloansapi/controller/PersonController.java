package br.com.srm.xloansapi.controller;

import br.com.srm.xloansapi.dto.LoanOperationDTO;
import br.com.srm.xloansapi.dto.PersonDTO;
import br.com.srm.xloansapi.dto.SavePersonDTO;
import br.com.srm.xloansapi.exceptions.BusinessRetireeRuleException;
import br.com.srm.xloansapi.exceptions.BusinessStudentRuleException;
import br.com.srm.xloansapi.exceptions.InvalidIdentificationException;
import br.com.srm.xloansapi.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Save new Person", description = "Save new Person")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = PersonDTO.class)))
    public ResponseEntity<PersonDTO> savePerson(@RequestBody SavePersonDTO savePersonDTO) throws BusinessStudentRuleException, BusinessRetireeRuleException, InvalidIdentificationException {
        return new ResponseEntity<>(personService.savePerson(savePersonDTO), HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "Update Person", description = "Update Person")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = PersonDTO.class)))
    public ResponseEntity<PersonDTO> updatePerson(@RequestBody SavePersonDTO savePersonDTO) throws BusinessStudentRuleException, BusinessRetireeRuleException, InvalidIdentificationException {
        return new ResponseEntity<>(personService.updatePerson(savePersonDTO), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get All Persons List", description = "Get All Persons List")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = PersonDTO.class)))
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        return new ResponseEntity<>(personService.getAllPersons(), HttpStatus.OK);
    }

    @DeleteMapping("/{personId}")
    @Operation(summary = "Delete Person", description = "Delete Person")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<Long> deletePerson(@PathVariable("personId") Long personId) {
        return new ResponseEntity<>(personService.deletePerson(personId), HttpStatus.OK);
    }

    @GetMapping("/{personId}")
    @Operation(summary = "Get Person By Id", description = "Get Person By Id")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = PersonDTO.class)))
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable("personId") Long personId) {
        return new ResponseEntity<>(personService.getPersonById(personId), HttpStatus.OK);
    }

}
