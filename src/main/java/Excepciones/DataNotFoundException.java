package Excepciones;

public class DataNotFoundException extends DataAccessException{
    public DataNotFoundException(String message) {
        super(message);
    }
}
