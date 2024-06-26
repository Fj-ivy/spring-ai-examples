package org.ivy.controller;

import jakarta.annotation.Resource;
import org.ivy.service.OnlineService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class OnlineController {

    @Resource
    private OnlineService onlineService;

    /**
     * Simple response generation.
     *
     * @param prompt 提示词
     * @return 返回内容
     */
    @GetMapping("/simple")
    public Flux<String> simple(String prompt) {
        return onlineService.simple(prompt);
    }

    /**
     * RAG-based response generation.
     *
     * @param prompt 提示词
     * @return 返回内容
     */
    @GetMapping("/rag")
    public Flux<String> rag(String prompt) {
        return onlineService.rag(prompt);
    }
}
