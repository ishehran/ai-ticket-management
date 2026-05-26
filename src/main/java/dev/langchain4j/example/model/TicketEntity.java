package dev.langchain4j.example.model;

import dev.langchain4j.example.ai_vocab.TicketStatus;
import dev.langchain4j.example.aiservice.intent.Priority;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tickets")
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;

    @Column(name = "assigned_to")
    private String assignedTo;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public TicketEntity() {
        super();
    }

    private TicketEntity(Builder builder) {
        id = builder.id;
        title = builder.title;
        description = builder.description;
        priority = builder.priority;
        status = builder.status;
        assignedTo = builder.assignedTo;
        createdAt = builder.createdAt;
        updatedAt = builder.updatedAt;
    }


    @PrePersist
    void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;

        if (this.status == null) {
            this.status = TicketStatus.OPEN;
        }

        if (this.priority == null || this.priority == Priority.UNKNOWN) {
            this.priority = Priority.MEDIUM;
        }

        if (this.assignedTo == null || this.assignedTo == "") {
            this.assignedTo = "Team A support";
        }
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static final class Builder {
        private Long id;
        private String title;
        private String description;
        private Priority priority;
        private TicketStatus status;
        private String assignedTo;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder setId(Long val) {
            id = val;
            return this;
        }

        public Builder setTitle(String val) {
            title = val;
            return this;
        }

        public Builder setDescription(String val) {
            description = val;
            return this;
        }

        public Builder setPriority(Priority val) {
            priority = val;
            return this;
        }

        public Builder setStatus(TicketStatus val) {
            status = val;
            return this;
        }

        public Builder setAssignedTo(String val) {
            assignedTo = val;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime val) {
            createdAt = val;
            return this;
        }

        public Builder setUpdatedAt(LocalDateTime val) {
            updatedAt = val;
            return this;
        }

        public TicketEntity build() {
            return new TicketEntity(this);
        }
    }
}
