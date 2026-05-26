package dev.langchain4j.example.aiservice;

import dev.langchain4j.agent.tool.Tool;
import io.micrometer.observation.annotation.Observed;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class AssistantTools {

    /**
     * This tool is available to {@link Assistant}
     */
    @Tool
    @Observed
    public String currentTime() {
        return LocalTime.now().toString();
    }

    @Tool
    @Observed
    public String applicationName(){
        return "LangChain4j Spring Boot application";
    }
}
