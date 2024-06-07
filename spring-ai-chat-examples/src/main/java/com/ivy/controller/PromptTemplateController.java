package com.ivy.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PromptTemplateController {

    @Resource
    private OpenAiChatModel openAiChatModel;


    @Value("classpath:film.st")
    private org.springframework.core.io.Resource template;

    @Value("classpath:code.st")
    private org.springframework.core.io.Resource codeTemplate;

    @GetMapping("/prompt")
    public String prompt(String director) {

        Map<String, Object> map = Map.of("director", director);
        PromptTemplate promptTemplate = new PromptTemplate(template, map);
        Prompt prompt = promptTemplate.create();

        ChatClient chatClient = ChatClient.builder(openAiChatModel)
                .build();
        return chatClient.prompt(prompt).call().content();
    }

    @GetMapping("/code")
    public String code(String language, String methodName, String description) {
        PromptTemplate promptTemplate = new PromptTemplate(codeTemplate);
        Prompt prompt = promptTemplate.create(
                Map.of("language", language, "methodName", methodName, "description", description)
        );
        ChatClient chatClient = ChatClient.builder(openAiChatModel)
                .build();
        return chatClient.prompt(prompt).call().content();
    }
}
