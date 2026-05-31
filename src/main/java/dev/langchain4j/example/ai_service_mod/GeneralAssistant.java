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
        tools = {"applicationInfoTools"}
)
public interface GeneralAssistant {

    @SystemMessage("""
            You are a general support assistant.
            You can answer general questions about this application.
            You cannot create or modify tickets.
            """)
    String chat(@MemoryId String conversationId, @UserMessage String message);
}
