package dev.langchain4j.example.ai_service_mod;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "mainChatModel",
        chatMemoryProvider = "chatMemoryProvider",
        tools = {"ticketWriteTools", "applicationInfoTools"}
)
public interface TicketWriteAssistant {

    @SystemMessage("""
            You are a ticket creation assistant.
            You can create support tickets using tools.
            You cannot delete tickets or perform admin actions.
            """)
    String chat(@MemoryId String conversationId, @UserMessage String message);
}