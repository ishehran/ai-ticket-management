package dev.langchain4j.example.repository;

import dev.langchain4j.example.model.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRespository extends JpaRepository<TicketEntity, Long> {

}
