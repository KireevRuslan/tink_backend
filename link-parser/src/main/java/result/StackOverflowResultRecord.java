package result;

public record StackOverflowResultRecord(String userId) implements ParseResult {
    @Override
    public String getResult() {
        return userId;
    }
}
