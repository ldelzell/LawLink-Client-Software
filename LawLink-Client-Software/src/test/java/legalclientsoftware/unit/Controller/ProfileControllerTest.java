package legalclientsoftware.unit.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import legal_client_software.business.GetClientUseCase;
import legal_client_software.controller.ProfileController;
import legal_client_software.domain.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ProfileControllerTest {

    private GetClientUseCase getClientUseCase;
    private ProfileController profileController;

    @BeforeEach
    void setUp() {
        getClientUseCase = mock(GetClientUseCase.class);
        profileController = new ProfileController(getClientUseCase);
    }

    @Test
    void getClient_ValidId_ReturnsClient() {

        long clientId = 1L;
        Client client = new Client();
        when(getClientUseCase.getClientByUserId(clientId)).thenReturn(Optional.of(client));

        ResponseEntity<Client> responseEntity = profileController.getClient(clientId);

        assertEquals(client, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getClient_InvalidId_ReturnsNotFound() {

        long clientId = 1L;
        when(getClientUseCase.getClientByUserId(clientId)).thenReturn(Optional.empty());

        ResponseEntity<Client> responseEntity = profileController.getClient(clientId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}
