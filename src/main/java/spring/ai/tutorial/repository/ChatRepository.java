package spring.ai.tutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.ai.tutorial.domain.Chat;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findByConversationIdOrderByCreatedAtAsc(String conversationId);

}
