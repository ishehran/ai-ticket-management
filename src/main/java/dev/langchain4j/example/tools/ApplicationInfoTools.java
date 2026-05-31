package dev.langchain4j.example.tools;

import dev.langchain4j.agent.tool.Tool;
import io.micrometer.observation.annotation.Observed;
import org.springframework.stereotype.Component;

@Component("applicationInfoTools")
public class ApplicationInfoTools {

    @Observed
    @Tool("Returns the name of this application")
    public String applicationName() {
        return "LangChain4j Spring Boot Learning Lab";
    }
}
