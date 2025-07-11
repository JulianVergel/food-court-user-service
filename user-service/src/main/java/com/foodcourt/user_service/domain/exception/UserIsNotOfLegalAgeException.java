package com.foodcourt.user_service.domain.exception;

public class UserIsNotOfLegalAgeException extends RuntimeException{
    public UserIsNotOfLegalAgeException() {
        super("El usuario no es mayor de edad");
    }
}
