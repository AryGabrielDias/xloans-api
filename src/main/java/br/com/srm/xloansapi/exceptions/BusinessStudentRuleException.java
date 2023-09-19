package br.com.srm.xloansapi.exceptions;

public class BusinessStudentRuleException extends Exception {

    private static final String US = "Rule for student identifier not met - ";
    private static final String BR = "Regra para identificador de estudante não atendida";

    @Override
    public String getMessage() {
        return US.concat(BR);
    }
}
