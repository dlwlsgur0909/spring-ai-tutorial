package spring.ai.tutorial.domain.service;

import com.openai.models.ChatModel;
import com.openai.models.embeddings.EmbeddingModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIService {

    private final ChatModel chatModel;
    private final EmbeddingModel embeddingModel;



}
