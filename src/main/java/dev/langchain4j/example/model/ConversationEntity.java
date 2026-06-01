package dev.langchain4j.example.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "conversation")
public class ConversationEntity {

    @Id
    @Column(name = "conversation_id", nullable = false, length = 100)
    private String conversationId;

    @Column(name = "title")
    private String title;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected ConversationEntity() {
    }

    public ConversationEntity(String conversationId, String title) {
        this.conversationId = conversationId;
        this.title = title;
    }

    @PrePersist
    void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getTitle() {
        return title;
    }
}
