package spring.ai.tutorial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import spring.ai.tutorial.domain.Chat;
import spring.ai.tutorial.dto.CityResponseDTO;
import spring.ai.tutorial.repository.ChatRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AIService {

    private final ChatClient chatClient;
    private final ChatMemoryRepository chatMemoryRepository;
    private final ChatRepository chatRepository;

    public String generate(String text) {
        return chatClient.prompt()
                .user(text)
                .call()
                .content();
    }

    // stream()에서는 structured output을 바로 적용할 수 없기에 call() 메서드에 적용
    public CityResponseDTO generateWithStructuredOutput(String text) {
        return chatClient.prompt()
                .user(text)
                .call()
                .entity(CityResponseDTO.class);
    }

    // 멀티턴 기능 구현 전
    public Flux<String> generateStreamWithoutMutiTurn(String text) {
        return chatClient.prompt()
                .user(text)
                .stream()
                .content();
    }

    // 멀티턴 기능 구현
    @Transactional
    public Flux<String> generateStreamWithMultiTurn(String text) {

        // 유저 & 페이지 별 ChatMemory를 관리하기 위한 ID
        String conversationId = "test" + "_" + "1";

        // 전체 대화 저장용
        Chat chatUser = new Chat(conversationId, text, MessageType.USER);

        // ChatMemory는 보통 빈으로 등록해서 사용
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(10) // 최대 들고있을 메세지 수
                .chatMemoryRepository(chatMemoryRepository)
                .build();

        /*
        advisor()를 통해서 MessageChatMemoryAdvisor를 등록하면
        자동으로 UserMessage와 AssistantMessage를 저장하기 때문에 chatMemory에 명시적으로 add 할 필요가 없다
         */
         // chatMemory.add(conversationId, new UserMessage(text)); // 신규 메세지 추가

        // 응답 메세지를 저장할 버퍼
        StringBuilder responseBuffer = new StringBuilder();

        return chatClient.prompt()
                .user(text)
                .advisors(advisorSpec ->
                        // ChatClient를 스프링에서 자동 생성하는 빈 대신 명시적으로 등록하면 advisor도 한번만 설정하면 된다
                        advisorSpec
                                .advisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                                .param(ChatMemory.CONVERSATION_ID, conversationId)
                )
                .stream()
                .content()
                .doOnNext(responseBuffer::append)
                .doOnComplete(() -> {
                    Chat chatAssistant = new Chat(conversationId, responseBuffer.toString(), MessageType.ASSISTANT);
                    chatRepository.saveAll(List.of(chatUser, chatAssistant));
                });
    }

    public List<Chat> readAllChats(String userId) {
        return chatRepository.findByConversationIdOrderByCreatedAtAsc(userId);
    }

}
