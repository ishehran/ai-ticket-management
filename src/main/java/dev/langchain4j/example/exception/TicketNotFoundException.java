package dev.langchain4j.example.exception;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(Long ticketId){
        super("Ticket not found for id: " + ticketId);
    }
}
