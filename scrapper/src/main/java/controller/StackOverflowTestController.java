package controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import result.StackOverflowResultRecord;
import model.response.StackOverflowQuestionInfoResponse;
import service.client.StackOverflowClient;

@RestController
@AllArgsConstructor
public class StackOverflowTestController {
    private final StackOverflowClient stackOverflowClient;

    @GetMapping("/stackoverflow/{id}")
    public StackOverflowQuestionInfoResponse getQuesInfo(@PathVariable Long id) {
        return stackOverflowClient.getStackOverflowQuestionInfo(new StackOverflowResultRecord(String.valueOf(id)))
                           .block();
    }

    @GetMapping("/stackoverflow")
    public StackOverflowQuestionInfoResponse getQuesInfoWithNull() {
        return stackOverflowClient.getStackOverflowQuestionInfo(null)
                                  .block();
    }
}
