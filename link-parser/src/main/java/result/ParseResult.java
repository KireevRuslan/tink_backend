package result;

public sealed interface ParseResult permits GitHubResultRecord, StackOverflowResultRecord {
    String getResult();
}