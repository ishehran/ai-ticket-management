package dev.langchain4j.example.config;

import dev.langchain4j.model.azure.AzureOpenAiChatModel;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static dev.langchain4j.model.chat.Capability.RESPONSE_FORMAT_JSON_SCHEMA;
import java.util.Set;

@Configuration
public class LlmConfiguration {

    @Primary
    @Bean("mainChatModel")
    ChatModel mainChatModel(
            @Value("${langchain4j.azure-open-ai.chat-model.endpoint}") String endpoint,
            @Value("${langchain4j.azure-open-ai.chat-model.api-key}") String apiKey,
            @Value("${langchain4j.azure-open-ai.chat-model.deployment-name}") String deploymentName
            ){
        return AzureOpenAiChatModel.builder()
                .endpoint(endpoint)
                .apiKey(apiKey)
                .deploymentName(deploymentName)
                .supportedCapabilities(Set.of(RESPONSE_FORMAT_JSON_SCHEMA))
                .strictJsonSchema(true)
                .build();
    }
}
