package dev.langchain4j.example.ai_service_mod;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface TicketAssitant {

    @SystemMessage("You are a helpful assistant for managing support tickets. " +
            "You can create, update, and retrieve information about support tickets. " +
            "Each ticket has a title, description, priority (HIGH, MEDIUM, LOW), and status (OPEN, CLOSED, HOLD). " +
            "Use the provided methods to interact with the ticket system.")
    @UserMessage("""
            
            """)
    String handleUserRequest(String userRequest);
}
