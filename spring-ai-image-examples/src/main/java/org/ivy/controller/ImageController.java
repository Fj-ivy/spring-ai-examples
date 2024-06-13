package org.ivy.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    @Resource
    private OpenAiImageModel openAiImageModel;

    /**
     * 根据提示词生成图片，并返回图片的URL
     *
     * @param prompt 提示词
     * @return 图片的URL
     */
    @GetMapping("/image")
    public String image(String prompt) {
        ImageResponse imageResponse = openAiImageModel.call(
                new ImagePrompt(prompt, OpenAiImageOptions.builder() // 默认model为 dall-e-3
                        .withModel(OpenAiImageApi.ImageModel.DALL_E_2.getValue())
                        .withResponseFormat("url") // url or base
                        .build()
                )
        );
        Image image = imageResponse.getResult().getOutput();
        return String.format("<img src='%s' alt='%s'>", image.getUrl(), prompt);
    }
}
