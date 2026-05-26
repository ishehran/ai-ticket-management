package dev.langchain4j.example.tools;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.example.aiservice.intent.Priority;
import dev.langchain4j.example.model.TicketEntity;
import dev.langchain4j.example.service.TicketService;
import org.springframework.stereotype.Component;

@Component
public class TicketTools {

    private final TicketService ticketService;

    public TicketTools(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Tool("Creates a support ticket")
    public TicketEntity createTicket(String issueSummary, Priority priority) {
        return ticketService.createTicket(issueSummary, priority);
    }
}
