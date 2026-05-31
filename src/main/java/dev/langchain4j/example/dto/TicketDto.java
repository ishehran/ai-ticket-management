package dev.langchain4j.example.dto;

import dev.langchain4j.example.ai_vocab.TicketStatus;
import dev.langchain4j.example.aiservice.intent.Priority;

import java.time.LocalDateTime;

public record TicketDto(
        Long id,
        String assigned_to,
        LocalDateTime created_at,
        LocalDateTime updated_at,
        Priority priority,
        TicketStatus status,
        String title,
        String description
) {

    public static final class Builder {
        private Long id;
        private String assigned_to;
        private LocalDateTime created_at;
        private LocalDateTime updated_at;
        private Priority priority;
        private TicketStatus status;
        private String title;
        private String description;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder setId(Long val) {
            id = val;
            return this;
        }

        public Builder setAssigned_to(String val) {
            assigned_to = val;
            return this;
        }

        public Builder setCreated_at(LocalDateTime val) {
            created_at = val;
            return this;
        }

        public Builder setUpdated_at(LocalDateTime val) {
            updated_at = val;
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

        public Builder setTitle(String val) {
            title = val;
            return this;
        }

        public Builder setDescription(String val) {
            description = val;
            return this;
        }

        public TicketDto build() {
            return new TicketDto(id,assigned_to,created_at,updated_at,priority,status,title,description);
        }
    }
}
