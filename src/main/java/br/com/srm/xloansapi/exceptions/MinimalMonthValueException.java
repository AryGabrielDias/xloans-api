package br.com.srm.xloansapi.exceptions;

public class MinimalMonthValueException extends Exception {
    private static final String US = "Minimal Installment amount not allowed for customer type - ";
    private static final String BR = "Valor de parcela mínima não permitida para o perfil do cliente";

    @Override
    public String getMessage() {
        return US.concat(BR);
    }
}
