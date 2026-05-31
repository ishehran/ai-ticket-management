package dev.langchain4j.example.aiservice;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "mainChatModel",
        chatMemoryProvider = "chatMemoryProvider"
)
public interface Assistant {

    @SystemMessage("""
            You are a practical support assistant inside a Spring Boot application.
            
            Rules:
            - Use tools when the user asks about tickets, application identity, or creating support tickets.
            - Do not invent ticket status.
            - If a ticket id is missing, ask the user for it.
            - When a ticket is created, clearly show the ticket id, priority, status, and assigned team.
            - Keep answers concise and useful.
            """)
    String chat(@MemoryId String conversationId,
                @UserMessage String userMessage);

    @SystemMessage("""
            You are a practical support assistant inside a Spring Boot application.

            Rules:
            - Use tools when the user asks about tickets, application identity, or creating support tickets.
            - Do not invent ticket status.
            - If a ticket id is missing, ask the user for it.
            - When a ticket is created, clearly show the ticket id, priority, status, and assigned team.
            - Keep answers concise and useful.
            """)
    Flux<String> stream(@MemoryId String conversationId,
                        @UserMessage String userMessage);
}