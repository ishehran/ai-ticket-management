package dev.langchain4j.example.tools;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.example.dto.TicketDto;
import dev.langchain4j.example.model.TicketEntity;
import dev.langchain4j.example.service.TicketService;
import io.micrometer.observation.annotation.Observed;
import org.springframework.stereotype.Component;

@Component("ticketReadTools")
public class TicketReadTools {

    private final TicketService ticketService;

    public TicketReadTools(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Observed
    @Tool("Returns ticket details by ticket id")
    public TicketDto getTicket(Long ticketId) {
        return ticketService.getTicket(ticketId);
    }
}
