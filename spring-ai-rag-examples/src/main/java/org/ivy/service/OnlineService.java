package org.ivy.service;

import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@Service
public class OnlineService {
    @Value("classpath:rag.st")
    private org.springframework.core.io.Resource ragTemplate;
    @Resource
    private OllamaChatModel chatModel;
    @Resource
    private VectorStore vectorStore;

    public Flux<String> simple(String prompt) {
        ChatClient client = ChatClient.builder(chatModel).build();
        return client.prompt()
                .user(prompt)
                .stream()
                .content();

    }

    public Flux<String> rag(String prompt) {
        // 检索
        SearchRequest searchRequest = SearchRequest.builder().query(prompt).similarityThreshold(0.8).build();
        List<Document> documents = vectorStore.similaritySearch(searchRequest);
        if (CollectionUtils.isEmpty(documents)) {
            return Flux.empty();
        }
        // 提示词生成
        List<String> context = documents.stream().map(Document::getFormattedContent).toList();
        SystemPromptTemplate promptTemplate = new SystemPromptTemplate(ragTemplate);
        Prompt p = promptTemplate.create(Map.of("context", context, "question", prompt));
        ChatClient chatClient = ChatClient.builder(chatModel).build();
        // 大模型生成内容
        return chatClient.prompt(p).stream().content();
    }
}
