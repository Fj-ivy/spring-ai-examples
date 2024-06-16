//package org.ivy.controller;
//
//import jakarta.annotation.Resource;
//import org.springframework.ai.image.Image;
//import org.springframework.ai.image.ImagePrompt;
//import org.springframework.ai.image.ImageResponse;
//import org.springframework.ai.zhipuai.ZhiPuAiImageModel;
//import org.springframework.ai.zhipuai.ZhiPuAiImageOptions;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class ZhiPuImageController {
//
//    @Resource
//    private ZhiPuAiImageModel zhiPuAiImageModel;
//
//
//    @GetMapping("/zhipu")
//    public String image(String prompt) {
//        ZhiPuAiImageOptions zhiPuAiImageOptions = ZhiPuAiImageOptions.builder()
//                .withModel("cogview-3") // 默认 cogview-3，目前仅支持这一个
//                .withUser("xxx") // 帮助 ZhiPuAI 监控和检测滥用行为，传用户user_id
//                .build();
//        ImageResponse imageResponse = zhiPuAiImageModel.call(new ImagePrompt(prompt, zhiPuAiImageOptions));
//        Image image = imageResponse.getResult().getOutput();
//        return String.format("<img src='%s' alt='%s'>", image.getUrl(), prompt);
//    }
//}
