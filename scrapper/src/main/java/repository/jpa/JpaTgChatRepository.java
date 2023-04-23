package repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import domain.entity.Chat;

public interface JpaTgChatRepository extends JpaRepository<Chat, Long> {
}