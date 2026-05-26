package dev.langchain4j.example.dto;

import dev.langchain4j.example.ai_vocab.TicketStatus;
import dev.langchain4j.example.aiservice.intent.Priority;

import java.time.LocalDateTime;

public record TicketDto(
        Long id,
        String assigned_to,
        LocalDateTime created_at,
        LocalDateTime updated_at,
        Priority priority,
        TicketStatus status

) {
}
