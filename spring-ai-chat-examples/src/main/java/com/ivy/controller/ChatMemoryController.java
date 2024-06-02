package com.ivy.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@RestController
public class ChatMemoryController {
//    private final List<Message> historyMessage = new ArrayList<>();

    @Resource
    private OpenAiChatModel openAiChatModel;

//    @GetMapping("/chatWithList")
//    public String chatWithList(String prompt) {
//        // 将用户消息添加到历史消息列表中
//        historyMessage.add(new UserMessage(prompt));
//        Generation result = openAiChatModel.call(new Prompt(historyMessage)).getResult();
//        // 将AI消息添加到历史消息列表中
//        AssistantMessage assistantMessage = result.getOutput();
//        historyMessage.add(assistantMessage);
//        return assistantMessage.getContent();
//    }

    private final ChatMemory chatMemory = new InMemoryChatMemory();
    @GetMapping("/chatWithChatMemory")
    public Flux<String> chatWithChatMemory(String chatId, String prompt) {
        ChatClient chatClient = ChatClient.builder(openAiChatModel)
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
