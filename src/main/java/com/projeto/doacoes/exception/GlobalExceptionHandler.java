package com.projeto.doacoes.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        log.error("Erro de validação: {}", e.getMessage());
        
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                "Os dados fornecidos são inválidos",
                LocalDateTime.now(),
                errors
        );
        
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("Argumento inválido: {}", e.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Argumento inválido",
                e.getMessage(),
                LocalDateTime.now(),
                null
        );
        
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        log.error("Erro de execução: {}", e.getMessage());
        
        // Não retornar detalhes internos em produção
        String message = e.getMessage().contains("não encontrada") ? 
                e.getMessage() : "Erro interno do servidor";
        
        HttpStatus status = e.getMessage().contains("não encontrada") ? 
                HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
        
        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                "Erro de execução",
                message,
                LocalDateTime.now(),
                null
        );
        
        return ResponseEntity.status(status).body(errorResponse);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        log.error("Erro não tratado: ", e);
        
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno",
                "Ocorreu um erro interno no servidor",
                LocalDateTime.now(),
                null
        );
        
        return ResponseEntity.internalServerError().body(errorResponse);
    }
    
    @Data
    public static class ErrorResponse {
        private int status;
        private String error;
        private String message;
        private LocalDateTime timestamp;
        private Map<String, String> validationErrors;
        
        public ErrorResponse(int status, String error, String message, 
                           LocalDateTime timestamp, Map<String, String> validationErrors) {
            this.status = status;
            this.error = error;
            this.message = message;
            this.timestamp = timestamp;
            this.validationErrors = validationErrors;
        }
    }
}