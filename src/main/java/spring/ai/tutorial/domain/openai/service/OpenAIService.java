package spring.ai.tutorial.domain.openai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.audio.tts.TextToSpeechResponse;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAIService {

    // yml 파일에 api 키 등록시 AutoConfig로 OpenAI 관련 @Bean들이 활성화 된다
    private final OpenAiChatModel openAiChatModel;
    private final OpenAiEmbeddingModel openAiEmbeddingModel;
    private final OpenAiImageModel openAiImageModel;
    private final OpenAiAudioSpeechModel openAiAudioSpeechModel;
    private final OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;

    // chat model method - response
    public String generate(String text) {

        // 메세지 종류 선언
        SystemMessage systemMessage = new SystemMessage("");
        UserMessage userMessage = new UserMessage(text);
        AssistantMessage assistantMessage = new AssistantMessage("");

        // 옵션 설정
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .model("gpt-4.1-mini")
                .temperature(0.7)
                .build();

        // 프롬프트 생성
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage, assistantMessage), options);

        // 요청
        ChatResponse response = openAiChatModel.call(prompt);

        // 응답
        return response.getResult().getOutput().getText();
    }

    // chat model method - response stream
    public Flux<String> generateStream(String text) {

        // 메세지 종류 선언
        SystemMessage systemMessage = new SystemMessage("");
        UserMessage userMessage = new UserMessage(text);
        AssistantMessage assistantMessage = new AssistantMessage("");

        // 옵션 설정
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .model("gpt-4.1-mini")
                .temperature(0.7)
                .build();

        // 프롬프트 생성
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage, assistantMessage), options);

        // 요청 및 응답
        return openAiChatModel.stream(prompt)
                .mapNotNull(chatResponse -> chatResponse.getResult().getOutput().getText());

    }

    // embedding model method
    public List<float[]> generateEmbedding(List<String> texts, String model) {

        OpenAiEmbeddingOptions embeddingOptions = OpenAiEmbeddingOptions.builder()
                .model(model)
                .build();

        EmbeddingRequest prompt = new EmbeddingRequest(texts, embeddingOptions);

        EmbeddingResponse response = openAiEmbeddingModel.call(prompt);

        return response.getResults().stream()
                .map(Embedding::getOutput)
                .toList();
    }

    // image model method
    public List<String> generateImages(String text, int count, int width, int height) {

        OpenAiImageOptions imageOptions = OpenAiImageOptions.builder()
                .quality("hd")
                .n(count)
                .width(width)
                .height(height)
                .build();

        ImagePrompt prompt = new ImagePrompt(text, imageOptions);

        ImageResponse response = openAiImageModel.call(prompt);

        // image 경로를 리스트로 반환
        return response.getResults().stream()
                .map(imageGeneration -> imageGeneration.getOutput().getUrl())
                .toList();
    }

    public byte[] tts(String text) {

        OpenAiAudioSpeechOptions speechOptions = OpenAiAudioSpeechOptions.builder()
                .responseFormat(OpenAiAudioSpeechOptions.AudioResponseFormat.MP3)
                .speed(1.0)
                .model("tts-1")
                .build();

        TextToSpeechPrompt prompt = new TextToSpeechPrompt(text, speechOptions);

        TextToSpeechResponse response = openAiAudioSpeechModel.call(prompt);
        return response.getResult().getOutput();
    }

}

