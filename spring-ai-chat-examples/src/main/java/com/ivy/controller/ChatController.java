package com.ivy.controller;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
public class ChatController {

    private final OpenAiChatModel openAiChatModel;

    public ChatController(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }

    @GetMapping("/chat")
    public String chat(String prompt) {
        return openAiChatModel.call(prompt);
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(String prompt) {
        return openAiChatModel.stream(prompt);
    }
}
