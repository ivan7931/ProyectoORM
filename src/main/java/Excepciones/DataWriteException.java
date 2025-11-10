package Excepciones;

public class DataWriteException extends DataAccessException{
    public DataWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
