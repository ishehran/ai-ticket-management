package dev.langchain4j.example.service;

import dev.langchain4j.example.ai_service_mod.GeneralAssistant;
import dev.langchain4j.example.ai_service_mod.ResponseAssistant;
import dev.langchain4j.example.ai_service_mod.TicketReadAssistant;
import dev.langchain4j.example.ai_service_mod.TicketWriteAssistant;
import dev.langchain4j.example.aiservice.Assistant;
import dev.langchain4j.example.aiservice.intent.IntentExtractor;
import dev.langchain4j.example.aiservice.intent.SupportIntent;
import dev.langchain4j.example.dto.TicketDto;
import dev.langchain4j.example.exception.InvalidTicketRequestException;
import dev.langchain4j.example.exception.TicketNotFoundException;
import dev.langchain4j.example.model.TicketEntity;
import org.springframework.stereotype.Service;

@Service
public class TicketWorkflowService {

    private final IntentExtractor intentExtractor;
    private final TicketService ticketService;
    private final Assistant assistant;
    private final ResponseAssistant responseAssistant;
    private final MySqlConversationStateService conversationStateService;
    private final TicketWriteAssistant ticketWriteAssistant;
    private final TicketReadAssistant ticketReadAssistant;
    private final GeneralAssistant generalAssistant;
    private final AgentAuditService agentAuditService;

    public TicketWorkflowService(
            IntentExtractor intentExtractor,
            TicketService ticketService,
            Assistant assistant,
            ResponseAssistant responseAssistant,
            MySqlConversationStateService conversationStateService,
            TicketWriteAssistant ticketWriteAssistant,
            TicketReadAssistant ticketReadAssistant,
            GeneralAssistant generalAssistant,
            AgentAuditService agentAuditService) {
        this.intentExtractor = intentExtractor;
        this.ticketService = ticketService;
        this.assistant = assistant;
        this.responseAssistant = responseAssistant;
        this.conversationStateService = conversationStateService;
        this.ticketWriteAssistant = ticketWriteAssistant;
        this.ticketReadAssistant = ticketReadAssistant;
        this.generalAssistant = generalAssistant;
        this.agentAuditService = agentAuditService;
    }

    private String userConversationId;
    private String userMessage;

    public String handle(String conversationId, String message) {
        if (conversationId == null || conversationId.isBlank()) {
            throw new IllegalArgumentException("Conversation ID cannot be null or blank");
        }
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message cannot be null or blank");
        }
        userConversationId = conversationId;
        userMessage = message;
        SupportIntent intent = intentExtractor.extract(message);
        try{


            if (intent.missingRequiredInformation()) {
                return intent.clarificationQuestion();
            }

            return switch (intent.action()) {
                case CREATE_TICKET -> ticketWriteAssistant.chat(conversationId, message);
                case CHECK_TICKET_STATUS -> ticketReadAssistant.chat(conversationId, message);
                case ASK_APPLICATION_NAME, GENERAL_CHAT -> generalAssistant.chat(conversationId, message);
                case OUT_OF_SCOPE -> "I can help with ticket creation and getting the status of it!!";
                case UNKNOWN -> "I am not clear can you tell me with more detail.";
            };
        }
        catch (TicketNotFoundException e){
            return "Ticket not found";
        }
        catch (InvalidTicketRequestException e){
            return e.getMessage();
        }
        catch (Exception e){
            agentAuditService.failure(
                    conversationId,
                    intent == null ? "INTENT_EXTRACTION_OR_ROUTING" : intent.action().name(),
                    null,
                    message,
                    e
            );
            return "Something went wrong whiele processing your request";
        }
    }

    public String oldHandle(String conversationId, String message) {
        if (conversationId == null || conversationId.isBlank()) {
            throw new IllegalArgumentException("Conversation ID cannot be null or blank");
        }
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message cannot be null or blank");
        }
        userConversationId = conversationId;
        userMessage = message;
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
        try{
            TicketDto ticket = ticketService.createTicket(
                    intent.issueSummary(),
                    intent.priority()
            );
            //ConversationStateService
            conversationStateService.markLastCreatedTicket(userConversationId, ticket.id());

            agentAuditService.success(
                    userConversationId,
                    "CREATE_TICKET",
                    "DIRECT_WORKFLOW",
                    intent,
                    ticket
            );
            String backendResult = "Ticket created successfully. Ticket ID: "
                    + ticket.id()
                    + ", Status: "
                    + ticket.status()
                    + ", Priority: "
                    + ticket.priority()
                    + ", Issue: "
                    + ticket.description();

            return handleResponse(userConversationId, userMessage, backendResult);
        }
        catch (Exception e){
            agentAuditService.failure(
                    userConversationId,
                    "CREATE_TICKET",
                    "DIRECT_WORKFLOW",
                    intent,
                    e
            );
            return "Something went wrong whiele processing your request";
        }
    }

    private String handleResponse(String conversationId, String userMessage, String backendResult){
        return responseAssistant.respond(
                conversationId,
                """
                User message:
                %s
                
                Backend result:
                %s
                
                Write the final response to the user.
                """
                .formatted(userMessage, backendResult)
        );
    }
    private String checkTicketStatus(SupportIntent intent) {
        try{
            Long ticketId = resolveTicketId(userConversationId, intent);
            if(ticketId == null) {
                return "Ticket ID not found";
            }
            if(intent.ticketId() != null && !intent.ticketId().isEmpty()) {
                TicketDto ticket = ticketService.getTicket(Long.valueOf(intent.ticketId()));
                conversationStateService.markLastViewedTicket(userConversationId, ticketId);
                return ticket.toString();
            }
            TicketDto ticket = ticketService.getTicket(ticketId);
            conversationStateService.markLastViewedTicket(userConversationId, ticketId);
            agentAuditService.success(
                    userConversationId,
                    "CHECK_TICKET_STATUS",
                    "DIRECT_WORKFLOW",
                    intent,
                    ticket
            );
            return handleResponse(userConversationId, userMessage, ticket.toString());
        }
        catch (Exception e){
            agentAuditService.failure(
                    userConversationId,
                    "CHECK_TICKET_STATUS",
                    "DIRECT_WORKFLOW",
                    intent,
                    e
            );
            return "Something went wrong whiele processing your request";
        }

    }
    private Long resolveTicketId(String conversationId, SupportIntent intent) {
        if (intent.ticketId() != null && !intent.ticketId().isBlank()) {
            return Long.valueOf(intent.ticketId());
        }

        return switch (intent.ticketReference()) {
            case LAST_CREATED, RECENT ->
                    conversationStateService.getLastCreatedTicketId(conversationId);

            case LAST_VIEWED ->
                    conversationStateService.getLastViewedTicketId(conversationId);

            case NONE, EXPLICIT_ID ->
                    null;
        };
    }
}
