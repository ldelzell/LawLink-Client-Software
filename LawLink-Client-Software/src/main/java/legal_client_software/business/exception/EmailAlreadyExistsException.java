package legal_client_software.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmailAlreadyExistsException extends ResponseStatusException {
    public EmailAlreadyExistsException(){ super(HttpStatus.BAD_REQUEST, "Email_Already_Exists");}
}
