package dev.langchain4j.example.aiservice.intent;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface IntentExtractor {

    @SystemMessage("""
            You are an intent extraction component inside a Spring Boot support assistant.

            Your job is not to answer the user.
            Your job is to classify the user's message into a structured SupportIntent object.

            Rules:
            - Do not invent ticket ids.
            - If the user asks about a ticket but does not provide a ticket id,
              set action to CHECK_TICKET_STATUS and missingRequiredInformation to true.
            - If the user wants to create a ticket but does not describe the issue,
              set action to CREATE_TICKET and missingRequiredInformation to true.
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