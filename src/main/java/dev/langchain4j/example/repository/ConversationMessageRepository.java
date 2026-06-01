package dev.langchain4j.example.repository;

import dev.langchain4j.example.model.ConversationMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationMessageRepository extends JpaRepository<ConversationMessageEntity, Long> {

    List<ConversationMessageEntity> findByConversationIdOrderByCreatedAtAsc(String conversationId);

}
