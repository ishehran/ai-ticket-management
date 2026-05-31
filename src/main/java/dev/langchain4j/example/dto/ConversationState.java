package dev.langchain4j.example.dto;

public record ConversationState(
        String conversationId,
        Long lastCreatedTicketId,
        Long lastViewedTicketId
) {
}
