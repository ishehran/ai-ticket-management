package dev.langchain4j.example.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "conversation_state")
public class ConversationStateEntity {

    @Id
    @Column(name = "conversation_id", nullable = false, length = 100)
    private String conversationId;

    @Column(name = "last_created_ticket_id")
    private Long lastCreatedTicketId;

    @Column(name = "last_viewed_ticket_id")
    private Long lastViewedTicketId;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected ConversationStateEntity() {
    }

    public ConversationStateEntity(String conversationId) {
        this.conversationId = conversationId;
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    @PreUpdate
    void touch() {
        this.updatedAt = LocalDateTime.now();
    }

    public String getConversationId() {
        return conversationId;
    }

    public Long getLastCreatedTicketId() {
        return lastCreatedTicketId;
    }

    public Long getLastViewedTicketId() {
        return lastViewedTicketId;
    }

    public void markLastCreatedTicket(Long ticketId) {
        this.lastCreatedTicketId = ticketId;
        this.lastViewedTicketId = ticketId;
    }

    public void markLastViewedTicket(Long ticketId) {
        this.lastViewedTicketId = ticketId;
    }
}
