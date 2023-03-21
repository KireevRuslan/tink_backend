package ap.backend;

public sealed interface Links permits GitHubURL, StackOverflowURL {
    public default String getData(String URL){
        return "null";
    }
}
