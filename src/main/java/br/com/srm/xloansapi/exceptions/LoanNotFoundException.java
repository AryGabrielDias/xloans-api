package br.com.srm.xloansapi.exceptions;

public class LoanNotFoundException extends Exception {

    private static final String US = "Loan not found - ";
    private static final String BR = "Empréstimo não encontrado";

    @Override
    public String getMessage() {
        return US.concat(BR);
    }
}
