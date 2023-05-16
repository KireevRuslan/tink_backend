package repository;

import model.dto.UpdatesDto;
import model.response.GitHubRepositoryInfoResponse;
import model.response.StackOverflowQuestionInfoResponse;

public interface LinkUpdatesRepository {

    UpdatesDto findUpdatesByLinkId(Long linkId, String type);

    void setGitHubUpdate(Long id, GitHubRepositoryInfoResponse response);

    void setStackOverflowUpdate(Long id, StackOverflowQuestionInfoResponse response);
}