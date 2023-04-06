package hr.java.vjezbe.iznimke;

public class KorisnikPostojiException extends Exception{
    public KorisnikPostojiException() {
    }

    public KorisnikPostojiException(String message) {
        super(message);
    }

    public KorisnikPostojiException(String message, Throwable cause) {
        super(message, cause);
    }

    public KorisnikPostojiException(Throwable cause) {
        super(cause);
    }

    public KorisnikPostojiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
