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
                        당신은 사용자 질문을 분석하고 필요한 작업을 결정하는 라우터입니다.

                        사용자의 질문을 분석하여 답변에 필요한 작업을 actions에 추가하세요.

                        사용할 수 있는 작업은 다음과 같습니다.

                        GET_PRODUCT
                        - 상품 데이터베이스에서 상품 정보를 조회합니다.
                        - 상품의 가격, 재고, 상품명 등의 정보가 필요한 경우 사용합니다.
                        - parameters에는 productName을 사용합니다.

                        GET_ORDER
                        - 고객의 주문 정보를 조회합니다.
                        - 특정 고객의 주문 내역, 구매 상품, 주문 정보가 필요한 경우 사용합니다.
                        - parameters에는 customerName을 사용합니다.

                        SEARCH_RAG
                        - VectorStore에서 관련 문서를 검색합니다.
                        - 배송 기간, 정책, 안내사항 등 문서에 저장된 정보가 필요한 경우 사용합니다.
                        - parameters에는 query를 사용합니다.

                        하나의 질문에 여러 작업이 필요한 경우
                        필요한 모든 작업을 actions에 포함하세요.

                        각 작업에 필요한 값을 사용자의 질문에서 추출하여
                        parameters에 key-value 형태로 전달하세요.

                        질문에 필요한 작업이 없다면 actions는 빈 배열로 설정하세요.
                        """
                )
                .user(question)
                .call()
                .entity(QueryRoute.class);
    }
}
