package dev.langchain4j.example.model;

import dev.langchain4j.example.ai_vocab.ConversationMessageRole;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "conversation_message")
public class ConversationMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conversation_id", nullable = false, length = 100)
    private String conversationId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConversationMessageRole role;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected ConversationMessageEntity() {
    }

    public ConversationMessageEntity(
            String conversationId,
            ConversationMessageRole role,
            String content
    ) {
        this.conversationId = conversationId;
        this.role = role;
        this.content = content;
    }

    @PrePersist
    void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getConversationId() {
        return conversationId;
    }

    public ConversationMessageRole getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
