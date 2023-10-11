package com.prazeres.domain.exceptionhandler;

public class NegocioException extends RuntimeException{
    public NegocioException(String message) {
        super(message);
    }

}
