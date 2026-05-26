package dev.langchain4j.example.aiservice.intent;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IntentController {

    private final IntentExtractor intentExtractor;

    public IntentController(IntentExtractor intentExtractor) {
        this.intentExtractor = intentExtractor;
    }

    @GetMapping("/assistant/intent")
    public SupportIntent extractIntent(
            @RequestParam(value = "message") String message
    ) {
        return intentExtractor.extract(message);
    }
}