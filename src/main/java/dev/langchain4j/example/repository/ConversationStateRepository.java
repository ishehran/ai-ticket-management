package dev.langchain4j.example.repository;

import dev.langchain4j.example.model.ConversationStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationStateRepository extends JpaRepository<ConversationStateEntity, String> {
}
