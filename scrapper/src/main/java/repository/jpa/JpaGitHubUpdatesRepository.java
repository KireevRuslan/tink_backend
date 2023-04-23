package repository.jpa;


import org.springframework.data.jpa.repository.JpaRepository;
import domain.entity.GitHubUpdates;

public interface JpaGitHubUpdatesRepository extends JpaRepository<GitHubUpdates, Long> {
}