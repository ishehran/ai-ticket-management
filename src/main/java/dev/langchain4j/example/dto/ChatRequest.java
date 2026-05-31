package dev.langchain4j.example.dto;

public record ChatRequest(
        String conversationId,
        String message
) {
}
