package com.clinica.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata exceções do tipo IllegalArgumentException, mapeando para o status HTTP 404 (Not Found)
     * e retornando um ProblemDetail padronizado com a mensagem do erro.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Recurso não encontrado");
        problem.setType(URI.create("https://clinica.com/errors/not-found"));
        return problem;
    }

    /**
     * Trata exceções de estado ilegal (regras de negócio violadas),
     * retornando o status HTTP 409 (Conflict).
     */
    @ExceptionHandler(IllegalStateException.class)
    public ProblemDetail handleIllegalState(IllegalStateException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problem.setTitle("Regra de negócio violada");
        problem.setType(URI.create("https://clinica.com/errors/conflict"));
        return problem;
    }

    /**
     * Trata erros de validação de dados em DTOs (anotações como @NotBlank, @NotNull),
     * mapeando os campos inválidos para um mapa e retornando o status HTTP 400 (Bad Request).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                erros.put(error.getField(), error.getDefaultMessage())
        );

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Campos inválidos na requisição");
        problem.setTitle("Erro de validação");
        problem.setProperty("erros", erros);
        return problem;
    }
}