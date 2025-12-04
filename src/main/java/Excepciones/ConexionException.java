package Excepciones;

public class ConexionException extends DataAccessException{
    public ConexionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConexionException(Throwable cause) {
        super(cause);
    }
}
