package dev.langchain4j.example.repository;

import dev.langchain4j.example.model.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<ConversationEntity,String> {
}
