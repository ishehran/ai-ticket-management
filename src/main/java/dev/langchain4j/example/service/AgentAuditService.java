package dev.langchain4j.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.example.model.AgentAuditLog;
import dev.langchain4j.example.repository.AgentAuditLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.util.SerializationUtils.serialize;

@Service
public class AgentAuditService {

    private final AgentAuditLogRepository agentAuditLogRepository;
    private final ObjectMapper objectMapper;

    public AgentAuditService(AgentAuditLogRepository agentAuditLogRepository, ObjectMapper objectMapper) {
        this.agentAuditLogRepository = agentAuditLogRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void success(String conversationId, String action, String toolName, Object requestPayload, Object responsePayload) {
        AgentAuditLog log = AgentAuditLog.Builder.builder()
                .setConversationId(conversationId)
                .setAction(action)
                .setToolName(toolName)
                .setRequestPayload(toJson(requestPayload))
                .setResponsePayload(toJson(responsePayload))
                .build();
        agentAuditLogRepository.save(log);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void failure(
            String conversationId,
            String action,
            String toolName,
            Object requestPayload,
            Exception exception
    ) {
        AgentAuditLog log = AgentAuditLog.Builder.builder()
                .setConversationId(conversationId)
                .setAction(action)
                .setToolName(toolName)
                .setRequestPayload(toJson(requestPayload))
                .setErrorMessage(exception.getMessage())
                .build();

        agentAuditLogRepository.save(log);
    }

    private String toJson(Object value) {
        if (value == null) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(value);
        }
        catch (JsonProcessingException e) {
            return "{\"serializationError\":\"Could not serialize payload\"}";
        }
    }

}
