package org.ivy.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MultiModalityController {

    @Value("classpath:img.png")
    private org.springframework.core.io.Resource imageResource;

    private final OllamaChatModel ollamaChatModel;

    public MultiModalityController(OllamaChatModel ollamaChatModel) {
        this.ollamaChatModel = ollamaChatModel;
    }

    @GetMapping("multi")
    public String multiModality(@RequestParam(defaultValue = "Explain what do you see on this picture?") String text) {
        ChatClient chatClient = ChatClient.builder(ollamaChatModel).build();
        var userMessage = new UserMessage(text, List.of(new Media(MimeTypeUtils.IMAGE_PNG, imageResource)));
        return chatClient.prompt(new Prompt(List.of(userMessage)))
                .call()
                .content();
    }

}
