package org.ivy.controller;

import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TranscriptionController {

    @Value("classpath:audio.mp3")
    private org.springframework.core.io.Resource audioResource;

    private final OpenAiAudioTranscriptionModel openAiTranscriptionModel;

    public TranscriptionController(OpenAiAudioTranscriptionModel openAiTranscriptionModel) {
        this.openAiTranscriptionModel = openAiTranscriptionModel;
    }

    @GetMapping("audio2Text")
    public String audio2Text() {
        var transcriptionOptions = OpenAiAudioTranscriptionOptions.builder()
                .withResponseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
                .withTemperature(0f)
                .build();
        AudioTranscriptionPrompt transcriptionRequest = new AudioTranscriptionPrompt(audioResource, transcriptionOptions);
        AudioTranscriptionResponse response = openAiTranscriptionModel.call(transcriptionRequest);
        return response.getResult().getOutput();
    }

}
