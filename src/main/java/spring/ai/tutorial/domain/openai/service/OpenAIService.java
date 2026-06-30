package spring.ai.tutorial.domain.openai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
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
    public List<float[]>

}
