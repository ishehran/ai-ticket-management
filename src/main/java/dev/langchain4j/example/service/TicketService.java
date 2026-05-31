package dev.langchain4j.example.service;

import dev.langchain4j.example.ai_vocab.TicketStatus;
import dev.langchain4j.example.aiservice.intent.Priority;
import dev.langchain4j.example.dto.TicketDto;
import dev.langchain4j.example.exception.TicketNotFoundException;
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
    public TicketDto createTicket(String issueSummary, Priority priority) {
        TicketEntity ticket = TicketEntity.Builder.builder()
                .setTitle(issueSummary)
                .setDescription(issueSummary)
                .setPriority(resolvePriority(priority))
                .setStatus(TicketStatus.OPEN)
                .build();

        TicketEntity newTicket = ticketRespository.save(ticket);
        return ticketResponse(newTicket);
    }

    @Transactional
    public TicketDto createTicket(String title, String issueSummary, Priority priority) {
        TicketEntity ticket = TicketEntity.Builder.builder()
                .setTitle(title)
                .setDescription(issueSummary)
                .setPriority(resolvePriority(priority))
                .setStatus(TicketStatus.OPEN)
                .build();

        TicketEntity newTicket = ticketRespository.save(ticket);
        return ticketResponse(newTicket);
    }

    private TicketDto ticketResponse(TicketEntity ticket) {
        return TicketDto.Builder.builder()
                .setId(ticket.getId())
                .setTitle(ticket.getTitle())
                .setDescription(ticket.getDescription())
                .setPriority(ticket.getPriority())
                .setStatus(ticket.getStatus())
                .setAssigned_to(ticket.getAssignedTo())
                .setCreated_at(ticket.getCreatedAt())
                .setUpdated_at(ticket.getUpdatedAt())
                .build();
    }

    @Transactional(readOnly = true)
    public TicketDto getTicket(Long ticketId) {
        TicketEntity ticket = ticketRespository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(ticketId));
        return ticketResponse(ticket);
    }

    private Priority resolvePriority(Priority priority) {
        if(priority == null || priority == Priority.UNKNOWN){
            return Priority.LOW;
        }
        return priority;
    }
}
