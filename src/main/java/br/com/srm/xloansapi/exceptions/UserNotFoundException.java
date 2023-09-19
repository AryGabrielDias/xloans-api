package br.com.srm.xloansapi.exceptions;

public class UserNotFoundException extends Exception {

    private static final String US = "User not found - ";
    private static final String BR = "Usuário não encontrado";

    @Override
    public String getMessage() {
        return US.concat(BR);
    }
}
