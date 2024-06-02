package com.ivy;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class ChatClientResponseTest {

    @Resource
    private ChatModel chatModel;
    @Resource
    private ChatClient.Builder builder;

    @Test
    public void testChatClientResponse() {
        String result = chatModel.call("Tell me a joke");
        System.out.println(result);
    }

    @Test
    public void testChatClient() {
        ChatClient chatClient = builder.build();
        ChatResponse chatResponse = chatClient.prompt()
                .user("Hello, how can I help you?")
                .call()
                .chatResponse();

        System.out.println(chatResponse.getResult().getOutput().getContent());
    }
}
