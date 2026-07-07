package spring.ai.tutorial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class AIService {

    private final ChatClient chatClient;

    public String generate(String text) {
        return chatClient.prompt()
                .user(text)
                .call()
                .content();
    }

    public Flux<String> generateStream(String text) {
        return chatClient.prompt()
                .user(text)
                .stream()
                .content();
    }


}
