package spring.ai.tutorial.tools;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import spring.ai.tutorial.domain.Product;
import spring.ai.tutorial.dto.UserResponseDto;
import spring.ai.tutorial.service.ProductService;

@Component
@RequiredArgsConstructor
public class ChatTools {

    private final ProductService productService;

    // 현재는 간단하게 명시적으로 데이터를 넣었지만 실제로는 DB를 조회하는 기능을 사용할 수 있다
    @Tool(description =
            "Get full profile/personal information of the logged-in current user including " +
                    "name, age, address, phone number, and zip code"
    )
    public UserResponseDto getUserInfoTool() {
        return new UserResponseDto(
                "Kim", 20L, "서울시 서초구 방배로", "010-0000-0000", "10000"
        );
    }


    // 상품 조회 Tool
    @Tool(description =
                    """
                    상품 데이터베이스에서 상품 정보를 조회합니다.
                    사용자가 특정 상품의 가격, 재고, 상품명 등의 정보를 질문하면 이 도구를 사용합니다.
                    상품 정보가 필요한 경우 모델의 기억이나 추측으로 답변하지 말고 반드시 이 도구를 사용해야 합니다.
                    """
    )
    public Product getProduct(String productName) {
        return productService.getProduct(productName);
    }

}
