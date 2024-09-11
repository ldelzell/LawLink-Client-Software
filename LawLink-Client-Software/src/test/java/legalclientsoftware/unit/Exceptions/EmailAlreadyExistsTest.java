package legalclientsoftware.unit.Exceptions;

import legal_client_software.business.exception.EmailAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailAlreadyExistsTest {

    @Test
     void testConstructor_HappyFlow() {
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        String expectedMessage = "Email_Already_Exists";

        EmailAlreadyExistsException exception = new EmailAlreadyExistsException();

        assertEquals(expectedStatus, exception.getStatusCode());
        assertEquals(expectedMessage, exception.getReason());
    }

    @Test
     void testThrowingException_NonHappyFlow() {

        String expectedMessage = "Email_Already_Exists";

        EmailAlreadyExistsException exception = assertThrows(
                EmailAlreadyExistsException.class,
                () -> {
                    throw new EmailAlreadyExistsException();
                }
        );
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals(expectedMessage, exception.getReason());
    }
}
