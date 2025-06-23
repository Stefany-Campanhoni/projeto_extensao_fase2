package com.stefanycampanhoni.projeto_extensao_fase2.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Senha Inválida!");
    }
}
