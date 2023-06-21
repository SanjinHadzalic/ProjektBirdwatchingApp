package hr.java.vjezbe.iznimke;

/**
 * Exception koji se baca kada je unijet korisnik koji vec postoji u aplikaciji
 */
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
