package hr.java.vjezbe.iznimke;

public class NedozvoljenBrojException extends RuntimeException{

    public NedozvoljenBrojException() {
    }

    public NedozvoljenBrojException(String message) {
        super(message);
    }

    public NedozvoljenBrojException(String message, Throwable cause) {
        super(message, cause);
    }

    public NedozvoljenBrojException(Throwable cause) {
        super(cause);
    }

    public NedozvoljenBrojException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
