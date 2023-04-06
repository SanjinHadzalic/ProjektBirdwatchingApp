package hr.java.vjezbe.iznimke;

/**
 * baca se kada nije unesen odgovarujuca vrijednost za odredjenu varijablu
 */

public class NeispravanUnosException extends Exception{
    public NeispravanUnosException() {
    }

    public NeispravanUnosException(String message) {
        super(message);
    }

    public NeispravanUnosException(String message, Throwable cause) {
        super(message, cause);
    }

    public NeispravanUnosException(Throwable cause) {
        super(cause);
    }

    public NeispravanUnosException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
