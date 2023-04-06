package hr.java.vjezbe.iznimke;

public class LokalitetPostojiException extends RuntimeException {
    public LokalitetPostojiException() {
    }

    public LokalitetPostojiException(String message) {
        super(message);
    }

    public LokalitetPostojiException(String message, Throwable cause) {
        super(message, cause);
    }

    public LokalitetPostojiException(Throwable cause) {
        super(cause);
    }

    public LokalitetPostojiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
