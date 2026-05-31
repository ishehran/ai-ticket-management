package dev.langchain4j.example.dto;

public record UserPrompt(
        String message,
        String conversationId
) {
}
