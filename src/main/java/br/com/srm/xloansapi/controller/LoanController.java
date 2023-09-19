package br.com.srm.xloansapi.controller;

import br.com.srm.xloansapi.dto.LoanOperationDTO;
import br.com.srm.xloansapi.dto.SaveLoanDTO;
import br.com.srm.xloansapi.exceptions.*;
import br.com.srm.xloansapi.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v0/api/loan")
public class LoanController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanController.class);

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    @Operation(summary = "Contract new Loan", description = "Contract new Loan")
    @ApiResponse(responseCode = "200", description = "OK",
        content = @Content(schema = @Schema(implementation = LoanOperationDTO.class)))
    public ResponseEntity<LoanOperationDTO> takeOutALoan(@RequestBody SaveLoanDTO saveLoanDTO) throws UserNotFoundException, MaximalLoanValueException, InstallmentsNumberAboveException, MinimalMonthValueException {
        return new ResponseEntity<>(loanService.saveLoan(saveLoanDTO), HttpStatus.CREATED);
    }

    @GetMapping("/payoff/{loanId}")
    @Operation(summary = "Pay Off a Loan", description = "Pay Off a Loan")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = LoanOperationDTO.class)))
    public ResponseEntity<LoanOperationDTO> payOffLoan(@PathVariable("loanId") Long loanId) throws LoanNotFoundException {
        return new ResponseEntity<>(loanService.payLoan(loanId), HttpStatus.OK);
    }
}
