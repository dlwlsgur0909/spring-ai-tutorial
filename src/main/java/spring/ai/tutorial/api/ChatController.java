package spring.ai.tutorial.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import spring.ai.tutorial.service.AIService;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final AIService aiService;

    @GetMapping("/")
    public String chatPage() {
        return "chat";
    }

    @PostMapping("/chat")
    @ResponseBody
    public String chat(@RequestBody Map<String, String> body) {
        return aiService.generate(body.get("text"));
    }

    @PostMapping("/chat/stream")
    @ResponseBody
    public Flux<String> chatStream(@RequestBody Map<String, String> body) {
        return aiService.generateStream(body.get("text"));
    }
}
