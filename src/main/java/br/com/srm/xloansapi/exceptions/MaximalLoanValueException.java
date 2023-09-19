package br.com.srm.xloansapi.exceptions;

public class MaximalLoanValueException extends Exception {

    private static final String US = "Total amount not allowed for customer type - ";
    private static final String BR = "Valor total não permitido para o perfil do cliente";

    @Override
    public String getMessage() {
        return US.concat(BR);
    }
}
