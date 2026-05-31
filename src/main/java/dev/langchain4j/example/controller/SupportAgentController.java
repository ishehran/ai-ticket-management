package dev.langchain4j.example.controller;

import dev.langchain4j.example.dto.UserPrompt;
import dev.langchain4j.example.service.TicketWorkflowService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticket")
public class SupportAgentController {
    private final TicketWorkflowService ticketWorkflowService;

    public SupportAgentController(TicketWorkflowService ticketWorkflowService) {
        this.ticketWorkflowService = ticketWorkflowService;
    }

    @PostMapping("/query")
    public String queryTicket(@RequestBody UserPrompt userPrompt) {
        return ticketWorkflowService.oldHandle(userPrompt.conversationId(), userPrompt.message());
    }
}
