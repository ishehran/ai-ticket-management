package dev.langchain4j.example.controller;

import dev.langchain4j.example.aiservice.SupportTicketTools;
import dev.langchain4j.example.aiservice.intent.IntentExtractor;
import dev.langchain4j.example.aiservice.intent.SupportIntent;
import dev.langchain4j.example.service.TicketService;
import dev.langchain4j.example.service.TicketWorkflowService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ticket")
public class AgentController {
    private final TicketService ticketService;
    private final IntentExtractor intentExtractor;
    private final TicketWorkflowService ticketWorkflowService;

    public AgentController(TicketService ticketService, IntentExtractor intentExtractor, TicketWorkflowService ticketWorkflowService) {
        this.ticketService = ticketService;
        this.intentExtractor = intentExtractor;
        this.ticketWorkflowService = ticketWorkflowService;
    }

    @GetMapping("/query")
    public String queryTicket(
            @RequestParam(value = "conversationId", defaultValue = "default") String conversationId,
            @RequestParam("message") String message) {
        return ticketWorkflowService.handle(conversationId, message);
    }
}
