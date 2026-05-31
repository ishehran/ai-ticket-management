package dev.langchain4j.example.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.example.aiservice.intent.Priority;
import dev.langchain4j.example.dto.TicketDto;
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
    public TicketDto createTicket(
            @P("Short title for the ticket, maximum 80 characters") String title,
            @P("Detailed summary of the user's issue") String issueSummary,
            @P("Ticket priority. Allowed values: LOW, MEDIUM, HIGH") Priority priority
    ) {
        TicketDto ticketDto = ticketService.createTicket(title, issueSummary, priority);
        return ticketDto;
    }

    @Tool("It tells the name of the application")
    public String applicationName(){
        return "AI agent ticket management";
    }
}
