package spring.ai.tutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.ai.tutorial.domain.ChatEntity;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

    List<ChatEntity> findByUserIdOrderByCreatedAtAsc(String conversationId);

}
