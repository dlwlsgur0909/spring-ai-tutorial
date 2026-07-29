package spring.ai.tutorial.config;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.core.Ordered;
import reactor.core.publisher.Flux;

public class TestAdvisor implements CallAdvisor, StreamAdvisor {
    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        System.out.println("========== PROMPT ==========");
        System.out.println("System Params : " + chatClientRequest.context());

        chatClientRequest.prompt().getInstructions()
                .forEach(message -> {
                    System.out.println("----------------");
                    System.out.println(message.getMessageType());
                    System.out.println(message.getText());
                });

        System.out.println("============================");

        return callAdvisorChain.nextCall(chatClientRequest);
    }

    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest, StreamAdvisorChain streamAdvisorChain) {
        System.out.println("========== PROMPT ==========");

        chatClientRequest.prompt().getInstructions()
                .forEach(message -> {
                    System.out.println("----------------");
                    System.out.println(message.getMessageType());
                    System.out.println(message.getText());
                });

        System.out.println("============================");

        return streamAdvisorChain.nextStream(chatClientRequest);
    }

    @Override
    public String getName() {
        return "logging-advisor";
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
