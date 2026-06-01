package dev.langchain4j.example.controller;

import dev.langchain4j.example.model.ConversationMessageEntity;
import dev.langchain4j.example.service.ConversationHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversations")
public class ConversationController {

    private final ConversationHistoryService historyService;

    public ConversationController(ConversationHistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/{conversationId}/messages")
    public List<ConversationMessageEntity> messages(
            @PathVariable String conversationId
    ) {
        return historyService.getMessages(conversationId);
    }
}
