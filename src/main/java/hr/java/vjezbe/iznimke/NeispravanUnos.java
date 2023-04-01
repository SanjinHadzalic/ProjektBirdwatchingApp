package hr.java.vjezbe.iznimke;

/**
 * baca se kada nije unesen odgovarujuca vrijednost za odredjenu varijablu
 */

public class NeispravanUnos extends Exception{
    public NeispravanUnos() {
    }

    public NeispravanUnos(String message) {
        super(message);
    }

    public NeispravanUnos(String message, Throwable cause) {
        super(message, cause);
    }

    public NeispravanUnos(Throwable cause) {
        super(cause);
    }

    public NeispravanUnos(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
