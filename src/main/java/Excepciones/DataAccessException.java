package Excepciones;

public class DataAccessException extends Exception {
    /***
     * Error al acceder a la base de datos
     * @param message
     */
    public DataAccessException(String message) {
        super(message);
    }

    /***
     * Error al acceder a la base de datos
     * @param message
     * @param cause
     */
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }
}
