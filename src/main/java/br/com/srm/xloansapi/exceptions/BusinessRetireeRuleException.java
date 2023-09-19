package br.com.srm.xloansapi.exceptions;

public class BusinessRetireeRuleException extends Exception {

    private static final String US = "Rule for retiree identifier not met - ";
    private static final String BR = "Regra para identificador de aposentado não atendida";

    @Override
    public String getMessage() {
        return US.concat(BR);
    }
}
