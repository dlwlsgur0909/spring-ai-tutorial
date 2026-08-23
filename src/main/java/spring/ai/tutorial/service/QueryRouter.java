package spring.ai.tutorial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import spring.ai.tutorial.dto.QueryRoute;

@Service
@RequiredArgsConstructor
public class QueryRouter {

    private final ChatClient routeChatClient;

    public QueryRoute route(String question) {
        return routeChatClient.prompt()
                .system("""
                             당신은 사용자 질문을 분석하는 라우터입니다.
                             사용자의 질문을 보고 어떤 정보가 필요한지 판단하세요.
                             needProduct:
                             상품 데이터베이스에서 상품 정보를 조회해야 한다면 true.
                             상품의 가격, 재고, 상품명 등의 정보가 필요하면 true.
                             needRag:
                             VectorStore에 저장된 문서나 정책, 배송, 안내사항 등의 정보가
                             필요하다면 true.
                             두 정보가 모두 필요하다면 둘 다 true로 설정하세요.
                             정보가 필요하지 않다면 false로 설정하세요.
                        """
                )
                .user(question)
                .call()
                .entity(QueryRoute.class);
    }
}
