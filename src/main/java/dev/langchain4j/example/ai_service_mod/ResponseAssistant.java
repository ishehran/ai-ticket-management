package dev.langchain4j.example.ai_service_mod;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "mainChatModel",
        chatMemoryProvider = "chatMemoryProvider"
)
public interface ResponseAssistant {

    @SystemMessage("""
            You are a concise support assistant.
            You do not invent facts.
            Use only the backend result provided to you.
            Keep the answer short and clear
            """)
    public String respond(@MemoryId String conversationId,
                          @UserMessage String prompt);
}
