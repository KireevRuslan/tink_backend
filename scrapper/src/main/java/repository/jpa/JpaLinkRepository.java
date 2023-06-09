package repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import domain.entity.Chat;
import domain.entity.Link;

import java.util.List;
import java.util.Optional;

public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    Optional<Link> findByUrlAndChat(String url, Chat chat);

    void deleteByUrlAndChat(String url, Chat chat);

    List<Link> findAllByChat(Chat chat);

    @Query("SELECT c.links FROM Chat c LEFT JOIN c.links WHERE c.tgChatId = :tgChatId")
    Optional<Link> findLinksByChatId(Long tgChatId);

    @Query(value = "SELECT l1.* FROM link_info.link l1 " +
            "WHERE l1.id = (" +
            "SELECT l2.id FROM link_info.link l2 " +
            "WHERE l2.chat_id = l1.chat_id " +
            "ORDER BY l2.last_check " +
            "LIMIT 1);", nativeQuery = true)
    List<Link> findOneOldestLinkByLastCheckForEachUser();

}