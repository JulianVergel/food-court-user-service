package com.foodcourt.user_service.infrastructure.exception;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // Anotación que marca esta clase para manejar excepciones globalmente
public class GlobalExceptionHandler {
    // Maneja excepciones de tipo ValidationException (lanzadas desde el dominio/caso de uso)
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDetails> handleValidationException(ValidationException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST); // Código 400 Bad Request
    }

    // Maneja las excepciones cuando las validaciones de @Valid en los DTOs fallan
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())); // Recopila errores por campo

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                "Validation failed",
                request.getDescription(false),
                errors // Incluye los errores de validación detallados
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST); // Código 400 Bad Request
    }

    // Maneja cualquier otra excepción no capturada explícitamente
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                "An unexpected error occurred: " + ex.getMessage(), // Mensaje genérico o de depuración
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR); // Código 500 Internal Server Error
    }
}

// Record para estandarizar el formato de las respuestas de error
record ErrorDetails(LocalDateTime timestamp, String message, String details, Map<String, String> errors) {
    public ErrorDetails(LocalDateTime timestamp, String message, String details) {
        this(timestamp, message, details, null);
    }
}
