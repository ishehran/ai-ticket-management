package dev.langchain4j.example.aiservice.intent;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "mainChatModel",
        chatMemoryProvider = "chatMemoryProvider"
)
public interface IntentExtractor {

    @SystemMessage("""
            You are an intent extraction component inside a Spring Boot support assistant.

            Your job is not to answer the user.
            Your job is to classify the user's message into a structured SupportIntent object.

            Rules:
            - Do not invent ticket ids.
            - If the user asks about a ticket but does not provide a ticket id and user has not mentioned the ticket reference which is NONE,
              set action to CHECK_TICKET_STATUS and missingRequiredInformation to true. If user has mentioned the ticket reference which is other than NONE then make the missingRequiredInformation to false.
            - If the user wants to create a ticket but does not describe the issue,
              set action to CREATE_TICKET and missingRequiredInformation to true.
            - Use KNOWLEDGE_QUESTION when the user asks about support policy, ticket priority rules, SLA, troubleshooting guidance, or how the support process works.
            - If priority is not mentioned, use UNKNOWN.
            - Keep issueSummary short and practical.
            """)
    @UserMessage("""
            Extract the support intent from this user message:

            {{it}}
            """)
    SupportIntent extract(String userMessage);

    //Use this if you have multiple arguments to pass on
    @UserMessage("""
        Extract intent from this message:

        {{message}}

        Current user role: {{role}}
        """)
    SupportIntent extract(@V("message") String message,
                          @V("role") String role);
}