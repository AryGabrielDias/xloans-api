package br.com.srm.xloansapi.controller;

import br.com.srm.xloansapi.dto.LoanOperationDTO;
import br.com.srm.xloansapi.dto.SaveLoanDTO;
import br.com.srm.xloansapi.exceptions.*;
import br.com.srm.xloansapi.service.LoanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LoanController.class)
public class LoanControllerTest {

    @MockBean
    private LoanService loanService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new LoanController(loanService)).build();
    }

    @Test
    public void should_returnLoanOperationDTO_when_takeOutALoanIsCalled() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        var saveLoanDTO = new SaveLoanDTO();
        saveLoanDTO.setId(1L);
        saveLoanDTO.setPersonId(1L);
        saveLoanDTO.setInstallmentsNumber(20);
        saveLoanDTO.setLoanValue(25000.00);

        when(loanService.saveLoan(any())).thenReturn(new LoanOperationDTO());

        mockMvc.perform(post("/v0/api/loan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(saveLoanDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_returnLoanOperationDTO_when_payOffLoanIsCalled() throws Exception {
        when(loanService.payLoan(anyLong())).thenReturn(new LoanOperationDTO());
        mockMvc.perform(get("/v0/api/loan/payoff/1"))
                .andExpect(status().isOk());
    }
}
