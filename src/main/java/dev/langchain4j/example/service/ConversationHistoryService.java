package dev.langchain4j.example.service;

import dev.langchain4j.example.ai_vocab.ConversationMessageRole;
import dev.langchain4j.example.model.ConversationEntity;
import dev.langchain4j.example.model.ConversationMessageEntity;
import dev.langchain4j.example.repository.ConversationMessageRepository;
import dev.langchain4j.example.repository.ConversationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConversationHistoryService {

    private final ConversationRepository conversationRepository;
    private final ConversationMessageRepository messageRepository;

    public ConversationHistoryService(
            ConversationRepository conversationRepository,
            ConversationMessageRepository messageRepository
    ) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    @Transactional
    public void saveUserMessage(String conversationId, String message) {
        ensureConversationExists(conversationId);
        messageRepository.save(new ConversationMessageEntity(
                conversationId,
                ConversationMessageRole.USER,
                message
        ));
    }

    @Transactional
    public void saveAssistantMessage(String conversationId, String answer) {
        ensureConversationExists(conversationId);
        messageRepository.save(new ConversationMessageEntity(
                conversationId,
                ConversationMessageRole.ASSISTANT,
                answer
        ));
    }

    @Transactional(readOnly = true)
    public List<ConversationMessageEntity> getMessages(String conversationId) {
        return messageRepository.findByConversationIdOrderByCreatedAtAsc(conversationId);
    }

    private void ensureConversationExists(String conversationId) {
        conversationRepository.findById(conversationId)
                .orElseGet(() -> conversationRepository.save(
                        new ConversationEntity(conversationId, "New Conversation")
                ));
    }
}
