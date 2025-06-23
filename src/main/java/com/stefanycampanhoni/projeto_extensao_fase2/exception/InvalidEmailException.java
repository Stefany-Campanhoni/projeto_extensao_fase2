package com.stefanycampanhoni.projeto_extensao_fase2.exception;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        super("Mentor não encontrado!");
    }
}
