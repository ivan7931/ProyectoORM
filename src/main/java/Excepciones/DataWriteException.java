package Excepciones;

public class DataWriteException extends DataAccessException{

    public DataWriteException(String message, Throwable cause) {
        super(message, cause);
    }
    public DataWriteException(String message) {
        super(message);
    }

    public DataWriteException(Throwable cause) {
        super(cause);
    }
}
