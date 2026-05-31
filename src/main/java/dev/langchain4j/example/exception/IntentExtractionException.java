package dev.langchain4j.example.exception;

public class IntentExtractionException extends RuntimeException {

    public IntentExtractionException(String message, Throwable cause) {
        super(message, cause);
    }
}
