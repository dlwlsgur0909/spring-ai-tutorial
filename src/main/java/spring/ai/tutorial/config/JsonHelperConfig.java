package spring.ai.tutorial.config;

import org.springframework.ai.util.JsonHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonHelperConfig {

    /*
    Spring Framework 7 (Spring Boot 4)부터는 Jackson 3을 사용한다
    따라서, com.fasterxml.jackson 이 아닌 tools.jackson 경로의 ObjectMapper를 사용해야 한다
    하지만, Jackson 3에서는 ObjectMapper 대신 JSON 전용 JsonMapper 사용을 권장하고 있다
    더 나아가 Spring AI 2.0은 JsonHelper를 제공한다
    공식 문서에서는 JsonHelper를 JSON serialization/deserialization의 canonical way라고 설명한다
    그리고 기본적으로 Spring AI가 제공하는 JsonMapper를 사용한다.
     */
    @Bean
    public JsonHelper jsonHelper() {
        return new JsonHelper();
    }

}
