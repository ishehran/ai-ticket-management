package dev.langchain4j.example.service;

import dev.langchain4j.example.ai_vocab.TicketStatus;
import dev.langchain4j.example.aiservice.intent.Priority;
import dev.langchain4j.example.model.TicketEntity;
import dev.langchain4j.example.repository.TicketRespository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketService {
    private final TicketRespository ticketRespository;

    public TicketService(TicketRespository ticketRespository) {
        this.ticketRespository = ticketRespository;
    }

    @Transactional
    public TicketEntity createTicket(String issueSummary, Priority priority) {
        TicketEntity ticket = TicketEntity.Builder.builder()
                .setTitle(issueSummary)
                .setDescription(issueSummary)
                .setPriority(resolvePriority(priority))
                .setStatus(TicketStatus.OPEN)
                .build();

        return ticketRespository.save(ticket);
    }

    @Transactional(readOnly = true)
    public TicketEntity getTicket(Long ticketId) {
        return ticketRespository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found: " + ticketId));
    }

    private Priority resolvePriority(Priority priority) {
        if(priority == null || priority == Priority.UNKNOWN){
            return Priority.LOW;
        }
        return priority;
    }
}
