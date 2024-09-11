package legal_client_software.business.exception;

public class UnauthorizedDataAccessException extends RuntimeException {
    public UnauthorizedDataAccessException(String message) {
        super(message);
    }
}
