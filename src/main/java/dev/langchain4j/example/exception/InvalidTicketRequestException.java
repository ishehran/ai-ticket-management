package dev.langchain4j.example.exception;

public class InvalidTicketRequestException extends RuntimeException {

    public InvalidTicketRequestException(String message) {
        super(message);
    }
}
