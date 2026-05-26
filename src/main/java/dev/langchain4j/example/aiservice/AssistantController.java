package dev.langchain4j.example.aiservice;

import dev.langchain4j.example.aiservice.intent.IntentExtractor;
import dev.langchain4j.example.aiservice.intent.SupportIntent;
import dev.langchain4j.service.spring.AiService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

//Use chat for conversation.
//Use tools for actions.
//Use structured output for decisions.

/**
 * This is an example of using an {@link AiService}, a high-level LangChain4j API.
 */
@RestController
public class AssistantController {

//    private final Assistant assistant;
//    private final StreamingAssistant streamingAssistant;
//
//    public AssistantController(Assistant assistant, StreamingAssistant streamingAssistant) {
//        this.assistant = assistant;
//        this.streamingAssistant = streamingAssistant;
//    }

    private final Assistant assistant;
    private final IntentExtractor intentExtractor;
    public AssistantController(Assistant assistant, IntentExtractor intentExtractor) {

        this.assistant = assistant;
        this.intentExtractor = intentExtractor;
    }

    @GetMapping("/assistant")
    public String assistant(
            @RequestParam(value = "memoryId", defaultValue = "default") String memoryId,
            @RequestParam(value = "message", defaultValue = "What is the current time?") String message) {
        return assistant.chat(memoryId, message);
    }

    @GetMapping(value = "/assistant/stream" , produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamAssistant(
            @RequestParam(value = "memoryId", defaultValue = "default") String memoryId,
            @RequestParam(value = "message", defaultValue = "Hello") String message
            ){
               return assistant.stream(memoryId, message);
    }

    @GetMapping("/assistant/workflow")
    public String workflow(
            @RequestParam(value = "memoryId", defaultValue = "default") String memoryId,
            @RequestParam(value = "message") String message
    ) {
        SupportIntent intent = intentExtractor.extract(message);

        if (intent.missingRequiredInformation()) {
            return intent.clarificationQuestion();
        }

        return assistant.chat(memoryId, message);
    }

//    @GetMapping(value = "/streamingAssistant", produces = TEXT_EVENT_STREAM_VALUE)
//    public Flux<String> streamingAssistant(
//            @RequestParam(value = "message", defaultValue = "What is the current time?") String message) {
//        return streamingAssistant.chat(message);
//    }
}
