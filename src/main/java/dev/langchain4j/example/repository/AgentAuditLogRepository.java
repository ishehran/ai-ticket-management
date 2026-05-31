package dev.langchain4j.example.repository;

import dev.langchain4j.example.model.AgentAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentAuditLogRepository extends JpaRepository<AgentAuditLog, Long> {
}
