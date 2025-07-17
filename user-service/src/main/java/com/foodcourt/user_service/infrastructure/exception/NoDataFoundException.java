package com.foodcourt.user_service.infrastructure.exception;

public class NoDataFoundException extends RuntimeException{
    public NoDataFoundException() {
        super("No se encontraron datos en la base de datos");
    }
}
