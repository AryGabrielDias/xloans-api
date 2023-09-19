package br.com.srm.xloansapi.exceptions;

public class InvalidIdentificationException extends Exception {

    private static final String US = "Invalid identification - ";
    private static final String BR = "Identificação inválida";

    @Override
    public String getMessage() {
        return US.concat(BR);
    }
}
