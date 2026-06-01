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
        contentRetriever = "contentRetriever"
)
public interface KnowledgeAssistant {

    @SystemMessage("""
            You are a support knowledge assistant.
            
                        Use the retrieved knowledge base context to answer questions.
                        If the answer is not present in the retrieved context, say that you don't know from the available knowledge base.
                        Do not invent policies.
                        Keep answers concise and practical.
            
            """)
    String answer(@MemoryId String conversationId,
                  @UserMessage String quesiton);
}
