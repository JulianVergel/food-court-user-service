package com.foodcourt.user_service.domain.exception;

public class InvalidDomainDataException extends RuntimeException {
    public InvalidDomainDataException(String message) {
        super(message);
    }
}
