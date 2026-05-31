package dev.langchain4j.example.service;

import dev.langchain4j.example.dto.ConversationState;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConversationStateService {

    private final Map<String, ConversationState> states = new ConcurrentHashMap<>();

    public void markLastCreatedTicket(String conversationId, Long ticketId) {
        ConversationState current = getOrCreate(conversationId);

        states.put(conversationId, new ConversationState(
                conversationId,
                ticketId,
                ticketId
        ));
    }

    public void markLastViewedTicket(String conversationId, Long ticketId) {
        ConversationState current = getOrCreate(conversationId);

        states.put(conversationId, new ConversationState(
                conversationId,
                current.lastCreatedTicketId(),
                ticketId
        ));
    }

    public Long getLastCreatedTicketId(String conversationId) {
        return getOrCreate(conversationId).lastCreatedTicketId();
    }

    public Long getLastViewedTicketId(String conversationId) {
        return getOrCreate(conversationId).lastViewedTicketId();
    }

    private ConversationState getOrCreate(String conversationId) {
        return states.computeIfAbsent(
                conversationId,
                id -> new ConversationState(id, null, null)
        );
    }
}
