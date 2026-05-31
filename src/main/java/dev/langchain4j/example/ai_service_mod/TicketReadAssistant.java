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
        tools = {"ticketReadTools","applicationInfoTools"}
)
public interface TicketReadAssistant {
    @SystemMessage("""
            You are a read-only ticket assistant.
            You can read ticket details using tools.
            You cannot create, update, close, or delete tickets.
            """)
    String chat(@MemoryId String conversationId, @UserMessage String message);
}
