package legalclientsoftware.unit.impl;
import legal_client_software.business.GetClientsUseCase;
import legal_client_software.business.impl.GetClientsUseCaseImpl;
import legal_client_software.domain.GetClientsResponse;
import legal_client_software.domain.Client;
import legal_client_software.persistence.entity.ClientEntity;
import legal_client_software.persistence.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GetClientsUseCaseImplTest {

    private ClientRepository clientRepository;
    private GetClientsUseCase getClientsUseCase;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        getClientsUseCase = new GetClientsUseCaseImpl(clientRepository);
    }

    @Test
    void getClients_ReturnsClients() {
        ClientEntity client1 = ClientEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .caseId(100L)
                .build();

        ClientEntity client2 = ClientEntity.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .password("password456")
                .caseId(101L)
                .build();

        List<ClientEntity> clientEntities = Arrays.asList(client1, client2);
        when(clientRepository.findAll()).thenReturn(clientEntities);

        GetClientsResponse response = getClientsUseCase.getClients();

        List<Client> expectedClients = response.getClients();

        assertEquals(expectedClients, response.getClients());
    }

}
