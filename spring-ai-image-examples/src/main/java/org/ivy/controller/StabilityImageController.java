//package org.ivy.controller;
//
//import jakarta.annotation.Resource;
//import org.springframework.ai.image.Image;
//import org.springframework.ai.image.ImagePrompt;
//import org.springframework.ai.image.ImageResponse;
//import org.springframework.ai.stabilityai.StabilityAiImageModel;
//import org.springframework.ai.stabilityai.api.StabilityAiImageOptions;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
////@RestController
//public class StabilityImageController {
//
//    @Resource
//    private StabilityAiImageModel stabilityAiImageModel;
//
//    @GetMapping("/stability")
//    public String image(String prompt) {
//        // 大家可以自行研究
//        StabilityAiImageOptions stabilityAiImageOptions = StabilityAiImageOptions.builder()
////                .withModel() // 指定模型
////                .withHeight() // 指定生成图片的高
////                .withWidth() //  指定生成图片的宽
//                .withResponseFormat("image/png") //  Must be "application/json" or "image/png"
//                .build();
//        ImageResponse imageResponse = stabilityAiImageModel.call(new ImagePrompt(prompt, stabilityAiImageOptions));
//        Image image = imageResponse.getResult().getOutput();
//        return String.format("<img src='%s' alt='%s'>", image.getUrl(), prompt);
//    }
//}
