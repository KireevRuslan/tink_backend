package ap.backend;

public final class GitHubURL implements Links {
    public String getData(String URL){
        String[] splitedURL = URL.split("/");
        String feedback = "Name: "+splitedURL[3]+" Repo: "+splitedURL[4];
        return feedback;
    }
}
