package org.ivy.controller;

import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
public class AudioController {

    private final OpenAiAudioSpeechModel openAiAudioSpeechModel;
    public AudioController(OpenAiAudioSpeechModel openAiAudioSpeechModel) {
        this.openAiAudioSpeechModel = openAiAudioSpeechModel;
    }
    // 同步方式文本生成语音
    @GetMapping(value = "tts", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public byte[] speech(@RequestParam(defaultValue = "Hello, this is a text-to-speech example.") String text) {
        OpenAiAudioSpeechOptions speechOptions = OpenAiAudioSpeechOptions.builder()
                .withModel("tts-1") // 指定模型, 目前Spring AI支持一种tts-1，可以不配置
                .withVoice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY) // 指定生成的音色
                .withResponseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3) // 指定生成音频的格式
                .withSpeed(1.0f) // 指定生成速度
                .build();
        SpeechPrompt speechPrompt = new SpeechPrompt(text, speechOptions);
        SpeechResponse response = openAiAudioSpeechModel.call(speechPrompt);
        return response.getResult().getOutput(); // 返回语音byte数组
    }

    // 流式方式文本生成语音
    @GetMapping(value = "stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<byte[]> stream(@RequestParam(defaultValue = "Today is a wonderful day to build something people love!") String text) {
        OpenAiAudioSpeechOptions speechOptions = OpenAiAudioSpeechOptions.builder()
                .withResponseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .build();
        SpeechPrompt speechPrompt = new SpeechPrompt(text, speechOptions);
        Flux<SpeechResponse> stream = openAiAudioSpeechModel.stream(speechPrompt);
        return stream.map(speechResponse -> speechResponse.getResult().getOutput()).flatMapSequential(Flux::just);
    }
}
