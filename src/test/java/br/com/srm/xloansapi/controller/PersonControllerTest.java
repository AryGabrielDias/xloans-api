package br.com.srm.xloansapi.controller;

import br.com.srm.xloansapi.dto.PersonDTO;
import br.com.srm.xloansapi.dto.SavePersonDTO;
import br.com.srm.xloansapi.exceptions.BusinessRetireeRuleException;
import br.com.srm.xloansapi.exceptions.BusinessStudentRuleException;
import br.com.srm.xloansapi.exceptions.InvalidIdentificationException;
import br.com.srm.xloansapi.service.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @MockBean
    private PersonService personService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new PersonController(personService)).build();
    }

    @Test
    public void should_returnPersonDTO_when_savePersonIsCalled() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        var savePersonDTO = new SavePersonDTO();
        savePersonDTO.setId(1L);
        savePersonDTO.setName("Usuario PJ");
        savePersonDTO.setIdentification("10110110111111");
        savePersonDTO.setBirthDate("01/02/1979");

        when(personService.savePerson(any())).thenReturn(new PersonDTO());

        mockMvc.perform(post("/v0/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(savePersonDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_returnPersonDTO_when_updatePersonIsCalled() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        var updatePersonDTO = new SavePersonDTO();
        updatePersonDTO.setId(1L);
        updatePersonDTO.setName("Usuario PJ");
        updatePersonDTO.setIdentification("10110110111112");
        updatePersonDTO.setBirthDate("01/02/1979");

        when(personService.updatePerson(any())).thenReturn(new PersonDTO());

        mockMvc.perform(put("/v0/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatePersonDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void should_returnListPersonDTO_when_getAllPersonsIsCalled() throws Exception {
        when(personService.getAllPersons()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/v0/api/person")).andExpect(status().isOk());
    }

    @Test
    public void should_returnListPersonDTO_when_getPersonByIdIsCalled() throws Exception {
        when(personService.getPersonById(anyLong())).thenReturn(new PersonDTO());
        mockMvc.perform(get("/v0/api/person/1")).andExpect(status().isOk());
    }

    @Test
    public void should_returnListPersonDTO_when_deletePersonIsCalled() throws Exception {
        when(personService.deletePerson(anyLong())).thenReturn(anyLong());
        mockMvc.perform(delete("/v0/api/person/1")).andExpect(status().isOk());
    }
}
