package dev.langchain4j.example.service;

import dev.langchain4j.example.model.ConversationStateEntity;
import dev.langchain4j.example.repository.ConversationStateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MySqlConversationStateService extends ConversationStateService {

    private final ConversationStateRepository repository;

    public MySqlConversationStateService(ConversationStateRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void markLastCreatedTicket(String conversationId, Long ticketId) {
        ConversationStateEntity state = getOrCreate(conversationId);
        state.markLastCreatedTicket(ticketId);
        repository.save(state);
    }

    @Override
    @Transactional
    public void markLastViewedTicket(String conversationId, Long ticketId) {
        ConversationStateEntity state = getOrCreate(conversationId);
        state.markLastViewedTicket(ticketId);
        repository.save(state);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getLastCreatedTicketId(String conversationId) {
        return repository.findById(conversationId)
                .map(ConversationStateEntity::getLastCreatedTicketId)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getLastViewedTicketId(String conversationId) {
        return repository.findById(conversationId)
                .map(ConversationStateEntity::getLastViewedTicketId)
                .orElse(null);
    }

    private ConversationStateEntity getOrCreate(String conversationId) {
        return repository.findById(conversationId)
                .orElseGet(() -> new ConversationStateEntity(conversationId));
    }
}
