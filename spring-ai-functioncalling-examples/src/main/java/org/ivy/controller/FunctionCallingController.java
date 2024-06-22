package org.ivy.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@RestController
public class FunctionCallingController {
    private final OpenAiChatModel openAiChatModel;

    @Value("classpath:weather.st")
    private org.springframework.core.io.Resource weather;

    public FunctionCallingController(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }

    /**
     * 没有函数调用，看看返回结果
     *
     * @return 返回天气情况
     */
    @GetMapping("/noFunc")
    public Flux<String> noFunc(String prompt) {
        ChatClient chatClient = ChatClient.builder(openAiChatModel).build();
        return chatClient.prompt(new PromptTemplate(weather, Map.of("prompt", prompt)).create())
                .stream()
                .content();
    }

    /**
     * 调用函数，看看返回结果
     *
     * @return 天气状况
     */
    @GetMapping("/func")
    public Flux<String> func(String prompt) {
        UserMessage userMessage = new UserMessage(prompt + " 你可以调用函数：'WeatherInfo'");
        ChatClient chatClient = ChatClient.builder(openAiChatModel).build();
        return chatClient.prompt(new Prompt(
                List.of(userMessage),
                        OpenAiChatOptions.builder()
                                .withFunction("WeatherInfo")
                                .build()
                        )
                ).stream()
                .content();
    }
}
