package repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import domain.entity.StackOverflowUpdates;

public interface JpaStackOverflowUpdatesRepository extends JpaRepository<StackOverflowUpdates, Long> {
}