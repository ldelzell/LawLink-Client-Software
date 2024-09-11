package legalclientsoftware.unit.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import legal_client_software.business.*;
import legal_client_software.controller.ClientController;
import legal_client_software.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ClientControllerTest {

    private CreateClientUseCase createClientUseCase;
    private GetClientUseCase getClientUseCase;
    private DeleteClientUseCase deleteClientUseCase;
    private UpdateClientUseCase updateClientUseCase;
    private GetClientsUseCase getClientsUseCase;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        createClientUseCase = mock(CreateClientUseCase.class);
        getClientUseCase = mock(GetClientUseCase.class);
        deleteClientUseCase = mock(DeleteClientUseCase.class);
        updateClientUseCase = mock(UpdateClientUseCase.class);
        getClientsUseCase = mock(GetClientsUseCase.class);
        clientController = new ClientController(createClientUseCase, getClientUseCase, deleteClientUseCase,
                updateClientUseCase, getClientsUseCase);
    }

    @Test
    void createClient_ValidRequest_ReturnsCreated() {

        CreateClientRequest request = new CreateClientRequest();
        CreateClientResponse response = new CreateClientResponse(1L);
        when(createClientUseCase.createClient(request)).thenReturn(response);

        ResponseEntity<CreateClientResponse> result = clientController.createClient(request);

        assertSame(response, result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void getClient_ValidId_ReturnsClient() {

        long id = 1L;
        Client client = new Client();
        when(getClientUseCase.getClient(id)).thenReturn(Optional.of(client));

        ResponseEntity<Client> result = clientController.getClient2(id);

        assertSame(client, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getClient_InvalidId_ReturnsNotFound() {

        long id = 1L;
        when(getClientUseCase.getClient(id)).thenReturn(Optional.empty());

        ResponseEntity<Client> result = clientController.getClient2(id);

        assertNull(result.getBody());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void getClients_ValidRequest_ReturnsClients() {

        GetClientsResponse response = new GetClientsResponse();
        when(getClientsUseCase.getClients()).thenReturn(response);

        ResponseEntity<GetClientsResponse> result = clientController.getClients();

        assertSame(response, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void deleteClient_ValidId_ReturnsNoContent() {

        ResponseEntity<Void> result = clientController.deleteClient(1);

        assertNull(result.getBody());
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(deleteClientUseCase).deleteClient(1);
    }

    @Test
    void updateClient_ValidRequest_ReturnsOk() {

        long id = 1L;
        UpdateClientRequest request = new UpdateClientRequest();

        ResponseEntity<Void> result = clientController.updateClient(id, request);

        assertNull(result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(updateClientUseCase).updateClient(request);
    }
}
