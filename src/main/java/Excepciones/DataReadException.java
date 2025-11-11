package Excepciones;

public class DataReadException extends DataAccessException{
    public DataReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataReadException(Throwable cause) {
        super(cause);
    }

}
