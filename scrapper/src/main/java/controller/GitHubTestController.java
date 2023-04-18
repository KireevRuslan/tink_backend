package controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import result.GitHubResultRecord;
import model.response.GitHubRepositoryInfoResponse;
import service.client.GitHubClient;

@RestController
@AllArgsConstructor
public class GitHubTestController {
    private GitHubClient gitHubClient;

    @GetMapping("/github/{user}/{repo}")
    public GitHubRepositoryInfoResponse getRepoInfo(@PathVariable String user, @PathVariable String repo) {
        return gitHubClient.getGitHubRepositoryInfo(new GitHubResultRecord(user, repo))
                           .block();
    }

    @GetMapping("/github")
    public GitHubRepositoryInfoResponse getRepoInfoWithNull() {
        return gitHubClient.getGitHubRepositoryInfo(null)
                           .block();
    }
}
