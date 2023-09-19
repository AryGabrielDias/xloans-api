package br.com.srm.xloansapi.exceptions;

public class InstallmentsNumberAboveException extends Exception {

    private static final String US = "Total installments number allowed maximum 24 - ";
    private static final String BR = "Total de parcelas permitidas não pode ultrapassar 24";

    @Override
    public String getMessage() {
        return US.concat(BR);
    }
}
