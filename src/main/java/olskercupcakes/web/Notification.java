package olskercupcakes.web;

public class Notification {

    private Type type;
    private String message;

    public Notification(Type type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        ERROR,
        WARNING,
        SUCCESS
    }
}
