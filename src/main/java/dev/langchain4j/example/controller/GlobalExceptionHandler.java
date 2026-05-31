package dev.langchain4j.example.controller;

import dev.langchain4j.example.dto.ErrorResponse;
import dev.langchain4j.example.exception.InvalidTicketRequestException;
import dev.langchain4j.example.exception.TicketNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TicketNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTicketNotFoundException(TicketNotFoundException e) {
        return new ErrorResponse("TICKET_NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(InvalidTicketRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidTicketRequest(InvalidTicketRequestException exception) {
        return new ErrorResponse("INVALID_TICKET_REQUEST", exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneric(Exception exception) {
        return new ErrorResponse("INTERNAL_ERROR", "Something went wrong");
    }
}
