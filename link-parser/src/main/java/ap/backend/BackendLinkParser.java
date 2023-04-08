package ap.backend;

public record BackendLinkParser(String URL) {
    public String getData(){
        if (!(URL.contains("https://github.com/") || URL.contains("https://stackoverflow.com/questions/"))){
            return "null";
        }
        else {
            Links[] links = {new StackOverflowURL(), new GitHubURL()};
            int link_type = URL.contains("https://github.com/") ? 1:0;
            return links[link_type].getData(URL);
        }
    }
}
