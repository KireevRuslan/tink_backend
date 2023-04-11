package Dto;

public class BotRequest {

    private String message;

    public BotRequest() {
    }

    public BotRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}