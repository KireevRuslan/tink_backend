package ap.backend;

public final class StackOverflowURL implements Links{
    public String getData(String URL){
        return URL.split("/")[4];
    }
}
