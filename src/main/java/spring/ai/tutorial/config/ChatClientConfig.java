package spring.ai.tutorial.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    /*
     두 bean의 등록 방법에 무슨 차이가 있나?

     실질적인 차이는 기본으로 구성된 Builder를 사용할 것인가, 특정 ChatModel을 명시해서 만들 것인가 입니다.
     예를 들어 ChatModel이 하나뿐이라면 둘의 결과가 사실상 비슷할 수 있습니다.
     ChatClient.Builder 주입 방식은 Spring AI가 구성해준 Builder를 활용하는 것이고, ChatModel 주입 방식은 내가 사용할 ChatModel을 명시해서 Builder를 직접 생성하는 방식입니다.

     결국 같은 ChatModel을 사용하는거라면 1번 방식으로 변경해야 한다 
     */

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder.build();
    }

    @Bean
    public ChatClient routeChatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .build();
    }

    // 기본 설정 시 자동 등록이라, 수동 등록시 에러
//    @Bean
//    public ChatMemoryRepository chatMemoryRepository(JdbcTemplate jdbcTemplate, PlatformTransactionManager transactionManager) {
//        return JdbcChatMemoryRepository.builder()
//                .jdbcTemplate(jdbcTemplate)
//                .transactionManager(transactionManager)
//                .build();
//    }
}
