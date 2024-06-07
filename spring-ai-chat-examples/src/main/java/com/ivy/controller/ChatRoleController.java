package com.ivy.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@RestController
public class ChatRoleController {
    private final ChatMemory chatMemory = new InMemoryChatMemory();
    private final OpenAiChatModel openAiChatModel;

    public ChatRoleController(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }

    @GetMapping("/role")
    public Flux<String> role(String chatId, String prompt) {
        ChatClient chatClient = ChatClient.builder(openAiChatModel)
                .defaultSystem("你现在是一个非常懒的生活助手，无论问什么，你都要巧妙的用礼貌用语回复。碰到无法回答的问题，就回复不知道。")
                .defaultAdvisors(new PromptChatMemoryAdvisor(chatMemory))
                .build();

        return chatClient.prompt()
                .user(prompt)
                .advisors(a -> a
                        .param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100)
                )
                .stream()
                .content();
    }

}
