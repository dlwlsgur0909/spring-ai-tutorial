package spring.ai.tutorial.tools;

import org.springframework.ai.tool.annotation.Tool;
import spring.ai.tutorial.dto.UserResponseDto;

public class ChatTools {

    // 현재는 간단하게 명시적으로 데이터를 넣었지만 실제로는 DB를 조회하는 기능을 사용할 수 있다
    @Tool(description = "Retrieve current user's personal information including name, age, address, phone, etc")
    public UserResponseDto getUserInfoTool() {
        return new UserResponseDto(
                "Kim", 20L, "서울시 서초구 방배로", "010-0000-0000", "10000"
        );
    }

}
