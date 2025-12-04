package Excepciones;

public class QueryException extends DataAccessException {
    public QueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public QueryException(Throwable cause) {
        super(cause);
    }

    public QueryException(String message) {
        super(message);
    }
}
