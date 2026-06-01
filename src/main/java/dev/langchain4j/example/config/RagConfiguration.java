package dev.langchain4j.example.config;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.util.List;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

@Configuration
public class RagConfiguration {

//1. Create EmbeddingModel
//2. Load docs
//3. Split docs
//4. Embed chunks
//5. Store in InMemoryEmbeddingStore
//6. Create ContentRetriever

    @Bean("embeddingModel")
    EmbeddingModel embeddingModel(
            @Value("${app.open-ai.embedding.api-key}") String apiKey,
            @Value("${app.open-ai.embedding.model-name}") String deploymentName
    ){
        return OpenAiEmbeddingModel.builder()
                .apiKey(apiKey)
                .modelName(deploymentName)
                .build();
    }

    @Bean("embeddingStore")
    EmbeddingStore<TextSegment> embeddingStore(){
        return new InMemoryEmbeddingStore<>();
    }

    @Bean("contentRetriever")
    ContentRetriever contentRetriever(
            EmbeddingModel embeddingModel,
            EmbeddingStore<TextSegment> embeddingStore
    ){
        DocumentParser parser = new TextDocumentParser();

        Document priorityGuide = loadDocument(
                Path.of("src/main/resources/knowledge/ticket-priority-guide.txt"),
                parser
        );

        Document slaPolicy = loadDocument(
                Path.of("src/main/resources/knowledge/ticket-sla-policy.txt"),
                parser
        );

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(500, 100))
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();

        ingestor.ingest(List.of(priorityGuide, slaPolicy));

        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(3)
                .minScore(0.6)
                .build();
    }
}
