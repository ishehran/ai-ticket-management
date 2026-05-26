package dev.langchain4j.example.aiservice;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolMemoryId;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SupportTicketTools {
    private final AtomicInteger sequence = new AtomicInteger(2000);

    private final Map<String, Ticket> tickets = new ConcurrentHashMap<>();

    public SupportTicketTools(){
        tickets.put(
                "TCK-1001",
                new Ticket(
                        "TCK-1001",
                        "OPEN",
                        "Unable to login to the system",
                        "HIGH",
                        "Support Team A",
                        "2026-05-17T10:00:00"
                )
        );
        tickets.put(
                "TCK-1002",
                new Ticket(
                        "TCK-1002",
                        "IN_PROGRESS",
                        "Error when uploading files",
                        "MEDIUM",
                        "Support Team B",
                        "2026-05-17T10:00:00"
                )
        );

    }
    @Tool("Returns the name of the application")
    public String applicationName(){
        return "Ticket Support Application";
    }

    @Tool("Return support ticket detail for a given ticket id")
    public Ticket getTicketStatus(
            @P("Support ticket id, for example TCK-1001") String ticketId,
            @ToolMemoryId String memoryId
    ){
        System.out.println("Tool getTicketStatus called for memoryId = " + memoryId);
        Ticket ticket = tickets.get(ticketId.toUpperCase());
        if(ticket == null){
            return new Ticket(
                    ticketId,
                    "NOT_FOUND",
                    "No ticket exists with this id",
                    "UNKNOWN",
                    "UNKNOWN",
                    LocalDateTime.now().toString()
            );
        }
        return ticket;
    }

    @Tool("Creates a support ticket and returns the created ticket details")
    public Ticket createSupportTicket(
            @P("Issue description") String issue,
            @P("Priority, for example HIGH, MEDIUM, LOW") String priority,
            @ToolMemoryId String memoryId
    ){
        System.out.println("Tool createSupportTicket called for memoryId = " + memoryId);

        String normalizedPriority =normalizedPriority(priority);
        String ticketId = "TCK-" + sequence.incrementAndGet();
        Ticket ticket = new Ticket(
                ticketId,
                "OPEN",
                issue,
                normalizedPriority,
                "Support Team C",
                LocalDateTime.now().toString()
        );
        tickets.put(ticketId, ticket);
        return ticket;
    }

    private String normalizedPriority(String priority) {
        if(priority == null){
            return "MEDIUM";
        }
        String value = priority.trim().toUpperCase();
        return switch (value){
            case "LOW","MEDIUM","HIGH" -> value;
            default -> "MEDIUM";
        };
    }

    public record Ticket(
            String id,
            String status,
            String issue,
            String priority,
            String assignedTeam,
            String createdAt
    ){}
}
