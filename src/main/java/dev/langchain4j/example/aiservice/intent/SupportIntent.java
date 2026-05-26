package dev.langchain4j.example.aiservice.intent;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.langchain4j.model.output.structured.Description;

@Description("Structured intent extracted from a user's support chat message")
public record SupportIntent(

        // Required because every message must be classified into one backend route.
        @JsonProperty(required = true)
        @Description("""
                The main action requested by the user.
                Use CREATE_TICKET when the user wants to raise a new issue.
                Use CHECK_TICKET_STATUS when the user asks about an existing ticket.
                Use ASK_APPLICATION_NAME when the user asks what application or system this is.
                Use GENERAL_CHAT for greetings, thanks, or normal conversation.
                Use OUT_OF_SCOPE when the user clearly asks for something unsupported.
                Use UNKNOWN when the intent is unclear.
                """)
        SupportAction action,

        // Optional because not every message contains a ticket id.
        @Description("Support ticket id if present, for example TCK-1001. Null if not present.")
        String ticketId,

        // Optional because users often forget priority.
        // UNKNOWN means the user did not specify priority.
        @Description("Priority mentioned by the user. Use UNKNOWN when no priority is mentioned.")
        Priority priority,

        // Optional because only ticket creation messages need an issue summary.
        @Description("Short summary of the issue. Null if the user is not reporting an issue.")
        String issueSummary,

        // Required because backend workflow must know whether it is safe to continue.
        @JsonProperty(required = true)
        @Description("True when the assistant needs more information before taking action.")
        boolean missingRequiredInformation,

        // Optional because we only need this when missingRequiredInformation is true.
        @Description("Question to ask the user when required information is missing. Null otherwise.")
        String clarificationQuestion
) {
}