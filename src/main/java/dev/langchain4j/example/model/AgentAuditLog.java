package dev.langchain4j.example.model;

import dev.langchain4j.example.ai_vocab.AuditStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

@Table(name = "agent_audit_log")
@Entity
public class AgentAuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "conversation_id", nullable = false)
    private String conversationId;

    @Column(nullable = false)
    private String action;

    @Column(name = "tool_name")
    private String toolName;

    @Enumerated(EnumType.STRING)
    private AuditStatus auditStatus;

    @Column(name = "request_payload", columnDefinition = "TEXT")
    private String requestPayload;

    @Column(name = "response_payload", columnDefinition = "TEXT")
      private String responsePayload;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private AgentAuditLog(Builder builder) {
        id = builder.id;
        conversationId = builder.conversationId;
        action = builder.action;
        toolName = builder.toolName;
        auditStatus = builder.auditStatus;
        requestPayload = builder.requestPayload;
        responsePayload = builder.responsePayload;
        errorMessage = builder.errorMessage;
        createdAt = builder.createdAt;
    }

    @PrePersist
    void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public AgentAuditLog() {
        super();
    }

    public static final class Builder {
        private Long id;
        private String conversationId;
        private String action;
        private String toolName;
        private AuditStatus auditStatus;
        private String requestPayload;
        private String responsePayload;
        private String errorMessage;
        private LocalDateTime createdAt;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder setId(Long val) {
            id = val;
            return this;
        }

        public Builder setConversationId(String val) {
            conversationId = val;
            return this;
        }

        public Builder setAction(String val) {
            action = val;
            return this;
        }

        public Builder setToolName(String val) {
            toolName = val;
            return this;
        }

        public Builder setStatus(AuditStatus val) {
            auditStatus = val;
            return this;
        }

        public Builder setRequestPayload(String val) {
            requestPayload = val;
            return this;
        }

        public Builder setResponsePayload(String val) {
            responsePayload = val;
            return this;
        }

        public Builder setErrorMessage(String val) {
            errorMessage = val;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime val) {
            createdAt = val;
            return this;
        }

        public AgentAuditLog build() {
            return new AgentAuditLog(this);
        }
    }
}
