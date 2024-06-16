package org.ivy.controller;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class FunctionCallingController {
    private final ZhiPuAiChatModel zhiPuAiChatModel;

    @Value("classpath:weather.st")
    private org.springframework.core.io.Resource weather;


    public FunctionCallingController(ZhiPuAiChatModel zhiPuAiChatModel) {
        this.zhiPuAiChatModel = zhiPuAiChatModel;
    }

    /**
     * 没有函数调用，看看返回结果
     *
     * @return 返回天气情况
     */
    @GetMapping("/noFunc")
    public String noFunc(String prompt) {
        ChatResponse called = zhiPuAiChatModel.call(
                new PromptTemplate(weather, Map.of("prompt", prompt))
                        .create()
        );
        return called.getResult().getOutput().getContent();
    }

    /**
     * 调用函数，看看返回结果
     *
     * @return 天气状况
     */
    @GetMapping("/func")
    public String func(String prompt) {
        UserMessage userMessage = new UserMessage(prompt);
        ChatResponse response = zhiPuAiChatModel.call(
                new Prompt(List.of(userMessage), ZhiPuAiChatOptions.builder()
                        .withFunction("WeatherInfo").build())
        );

        return response.getResult().getOutput().getContent();
    }
}
