package dev.langchain4j.example.tools;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.example.aiservice.intent.Priority;
import dev.langchain4j.example.dto.TicketDto;
import dev.langchain4j.example.service.TicketService;
import io.micrometer.observation.annotation.Observed;
import org.springframework.stereotype.Component;

@Component("ticketWriteTools")
public class TicketWriteTools {

    private final TicketService ticketService;

    public TicketWriteTools(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Observed
    @Tool("Creates a support ticket")
    public TicketDto createTicket(String title, String description, Priority priority) {
        return ticketService.createTicket(title, description, priority);
    }
}
