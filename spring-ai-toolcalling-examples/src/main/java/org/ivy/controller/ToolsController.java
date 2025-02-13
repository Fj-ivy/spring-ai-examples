package org.ivy.controller;

import org.ivy.tools.DateTimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ToolsController {
    private final OllamaChatModel ollamaChatModel;

    public ToolsController(OllamaChatModel ollamaChatModel) {
        this.ollamaChatModel = ollamaChatModel;
    }

    @GetMapping("/search-tool")
    public String get(@RequestParam(value = "prompt", required = false) String prompt) {
        return ChatClient.create(ollamaChatModel)
                .prompt(prompt)
                .tools(new DateTimeTools())
                .call()
                .content();
    }
}
