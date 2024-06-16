//package org.ivy.controller;
//
//import jakarta.annotation.Resource;
//import org.springframework.ai.azure.openai.AzureOpenAiImageModel;
//import org.springframework.ai.azure.openai.AzureOpenAiImageOptions;
//import org.springframework.ai.image.Image;
//import org.springframework.ai.image.ImagePrompt;
//import org.springframework.ai.image.ImageResponse;
//import org.springframework.ai.openai.api.OpenAiImageApi;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
////@RestController
//public class AzureImageController {
//
//    @Resource
//    private AzureOpenAiImageModel azureOpenAiImageModel;
//
//    @GetMapping("/azure")
//    public String image(String prompt) {
//        // AzureOpenAiImageOptions 配置要求与OpenAiImageOptions一样，没有任何区别
//        AzureOpenAiImageOptions azureOpenAiImageOptions = AzureOpenAiImageOptions.builder()
//                .withModel(OpenAiImageApi.ImageModel.DALL_E_3.getValue())
//                .withResponseFormat("url") // url or base
//                .build();
//        ImageResponse imageResponse = azureOpenAiImageModel.call(new ImagePrompt(prompt, azureOpenAiImageOptions));
//        Image image = imageResponse.getResult().getOutput();
//        return String.format("<img src='%s' alt='%s'>", image.getUrl(), prompt);
//    }
//}
