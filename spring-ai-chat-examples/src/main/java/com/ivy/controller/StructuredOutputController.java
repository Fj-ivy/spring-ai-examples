package com.ivy.controller;

import com.ivy.model.Film;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.StructuredOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class StructuredOutputController {
    @Resource
    private OpenAiChatModel openAiChatModel;

    @GetMapping("/bean")
    public Film structuredOutput(String director) {
        // 定义提示词模版
        // 其中 format指定输出的格式
        final String template = """
                        请问{director}导演最受欢迎的电影是什么？哪年发行的，电影讲述的什么内容？
                        {format}
                """;
        // 定义结构化输出转化器, 生成Bean
        StructuredOutputConverter<Film> structured = new BeanOutputConverter<>(Film.class);
        // 生成提示词对象
        PromptTemplate promptTemplate = new PromptTemplate(template);
        Prompt prompt = promptTemplate.create(Map.of("director", director, "format", structured.getFormat()));

        ChatClient chatClient = ChatClient.builder(openAiChatModel)
                .build();
        String content = chatClient.prompt(prompt).call().content();
        // 转换
        return structured.convert(content);
    }


    @GetMapping("/bean2")
    public Film structuredOutput2(String director) {
        return ChatClient.create(openAiChatModel)
                .prompt()
                .user(u -> u.text("""
                                请问{director}导演最受欢迎的电影是什么？哪年发行的，电影讲述的什么内容
                        """).params(Map.of("director", director))
                ).call()
                .entity(Film.class);

    }

    /**
     * list output example, return a list of strings
     * Spring AI 也像BeanOutputConverter 一样也支持两种写法，这里只提供了一种写法示例，可以参考官方·文档
     *
     * @return
     */
    @GetMapping("/list")
    public List<String> structuredOutputList() {
        return ChatClient.create(openAiChatModel)
                .prompt()
                .user(u -> u.text("""
                                List five {subject}
                        """).params(Map.of("subject", "ice cream flavors"))
                ).call()
                .entity(new ListOutputConverter(new DefaultConversionService()));

    }

    @GetMapping("/map")
    public Map<String, Object> structuredOutputMap() {
        return ChatClient.create(openAiChatModel).prompt()
                .user(u -> u.text("Provide me a List of {subject}")
                        .param("subject", "an array of numbers from 1 to 9 under they key name 'numbers'"))
                .call()
                .entity(new ParameterizedTypeReference<>() {
                });
    }
}
