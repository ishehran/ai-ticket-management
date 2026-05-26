package dev.langchain4j.example.lowlevel;

import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is an example of using a {@link ChatLanguageModel}, a low-level LangChain4j API.
 */
@RestController
public class ChatModelController {

    private final ChatLanguageModel chatModel;

    public ChatModelController(ChatLanguageModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/model")
    public String model(@RequestParam(value = "message", defaultValue = "Hello") String message) {
        return chatModel.generate(message);
    }
}
