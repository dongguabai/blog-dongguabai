package blog.dongguabai.dongguabai.security.core.exception;

public class X5RequestHeadMissException extends X5RequestException {

    private String head;

    public X5RequestHeadMissException(String head, String message) {
        super(message);
        this.head = head;
    }
}