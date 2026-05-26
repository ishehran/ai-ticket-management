package dev.langchain4j.example.service;

import dev.langchain4j.example.aiservice.Assistant;
import dev.langchain4j.example.aiservice.intent.IntentExtractor;
import dev.langchain4j.example.aiservice.intent.SupportIntent;
import dev.langchain4j.example.model.TicketEntity;
import org.springframework.stereotype.Service;

@Service
public class TicketWorkflowService {

    private final IntentExtractor intentExtractor;
    private final TicketService ticketService;
    private final Assistant assistant;

    public TicketWorkflowService(
            IntentExtractor intentExtractor,
            TicketService ticketService,
            Assistant assistant
    ) {
        this.intentExtractor = intentExtractor;
        this.ticketService = ticketService;
        this.assistant = assistant;
    }

    public String handle(String conversationId, String message) {
        SupportIntent intent = intentExtractor.extract(message);

        if (intent.missingRequiredInformation()) {
            return intent.clarificationQuestion();
        }

        return switch (intent.action()) {
            case CREATE_TICKET -> createTicket(intent);
            case CHECK_TICKET_STATUS -> checkTicketStatus(intent);
            case ASK_APPLICATION_NAME, GENERAL_CHAT -> assistant.chat(conversationId, message);
            case OUT_OF_SCOPE -> "I can help with ticket creation and getting the status of it!!";
            case UNKNOWN -> "I am not clear can you tell me in more detail.";
        };
    }

    private String createTicket(SupportIntent intent) {
        TicketEntity ticket = ticketService.createTicket(
                intent.issueSummary(),
                intent.priority()
        );

        return "Ticket created successfully. Ticket ID: "
                + ticket.getId()
                + ", Status: "
                + ticket.getStatus()
                + ", Priority: "
                + ticket.getPriority();
    }

    private String checkTicketStatus(SupportIntent intent) {
        Long ticketId = Long.valueOf(intent.ticketId());
        TicketEntity ticket = ticketService.getTicket(ticketId);

        return "Ticket "
                + ticket.getId()
                + " status is "
                + ticket.getStatus()
                + ". Priority: "
                + ticket.getPriority()
                + ". Assigned to: "
                + ticket.getAssignedTo();
    }
}
