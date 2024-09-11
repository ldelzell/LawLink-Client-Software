package legalclientsoftware.unit.impl.Attorney;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import legal_client_software.business.attorney.impl.GetClientsByAttorneyIdUseCaseImpl;
import legal_client_software.domain.Client;
import legal_client_software.domain.attorney.GetClientsByAttorneyResponse;
import legal_client_software.persistence.entity.AttorneyEntity;
import legal_client_software.persistence.entity.ClientEntity;
import legal_client_software.persistence.repository.AttorneyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
 class GetClientsByAttorneyIdUseCaseImplTest {

    @Mock
    private AttorneyRepository attorneyRepository;

    @InjectMocks
    private GetClientsByAttorneyIdUseCaseImpl getClientsByAttorneyIdUseCase;

    private AttorneyEntity attorneyEntity;
    private ClientEntity clientEntity;

    @BeforeEach
    void setUp() {
        clientEntity = new ClientEntity();
        clientEntity.setId(1L);
        clientEntity.setFirstName("John");
        clientEntity.setLastName("Doe");
        clientEntity.setEmail("john.doe@example.com");
        clientEntity.setCaseId(1L);

        attorneyEntity = new AttorneyEntity();
        attorneyEntity.setId(1L);
        attorneyEntity.setClients(List.of(clientEntity));
    }

    @Test
    void testGetClientsByAttorneyId_AttorneyExists() {

        long attorneyId = 1L;
        when(attorneyRepository.findById(attorneyId)).thenReturn(Optional.of(attorneyEntity));


        GetClientsByAttorneyResponse response = getClientsByAttorneyIdUseCase.getClientsByAttorneyId(attorneyId);

        assertNotNull(response);
        assertNotNull(response.getClients());
        assertEquals(1, response.getClients().size());
        Client client = response.getClients().get(0);
        assertEquals(clientEntity.getId(), client.getId());
        assertEquals(clientEntity.getFirstName(), client.getFirstName());
        assertEquals(clientEntity.getLastName(), client.getLastName());
        assertEquals(clientEntity.getEmail(), client.getEmail());
        assertEquals(clientEntity.getCaseId(), client.getCaseId());

        verify(attorneyRepository, times(1)).findById(attorneyId);
    }

    @Test
    void testGetClientsByAttorneyId_AttorneyDoesNotExist() {

        long attorneyId = 1L;
        when(attorneyRepository.findById(attorneyId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            getClientsByAttorneyIdUseCase.getClientsByAttorneyId(attorneyId);
        });
        assertEquals("Attorney not found", exception.getMessage());
        verify(attorneyRepository, times(1)).findById(attorneyId);
    }
}
