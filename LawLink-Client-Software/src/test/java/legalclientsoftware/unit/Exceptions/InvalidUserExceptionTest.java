package legalclientsoftware.unit.Exceptions;

import legal_client_software.business.exception.InvalidUserException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InvalidUserExceptionTest {

    @Test
     void testConstructor_HappyFlow() {

        String expectedErrorCode = "SOME_ERROR_CODE";

        InvalidUserException exception = new InvalidUserException(expectedErrorCode);

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals(expectedErrorCode, exception.getReason());
    }

    @Test
     void testThrowingException_NonHappyFlow() {
        String expectedErrorCode = "SOME_ERROR_CODE";

        InvalidUserException exception = assertThrows(
                InvalidUserException.class,
                () -> {
                    throw new InvalidUserException(expectedErrorCode);
                }
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals(expectedErrorCode, exception.getReason());
    }
}
