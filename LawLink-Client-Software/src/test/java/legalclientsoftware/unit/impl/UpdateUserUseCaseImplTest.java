package legalclientsoftware.unit.impl;

import legal_client_software.business.exception.InvalidUserException;
import legal_client_software.business.impl.UpdateClientUseCaseImpl;
import legal_client_software.domain.UpdateClientRequest;
import legal_client_software.persistence.entity.ClientEntity;
import legal_client_software.persistence.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 class UpdateUserUseCaseImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private UpdateClientUseCaseImpl updateClientUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void updateClient_HappyPath() {

        UpdateClientRequest request = new UpdateClientRequest(1L, "John", "Doe", "john@example.com", "newPassword");
        ClientEntity existingClient = new ClientEntity();
        existingClient.setId(1L);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(existingClient));

        updateClientUseCase.updateClient(request);

        verify(clientRepository, times(1)).findById(1L);
        verify(clientRepository, times(1)).save(existingClient);
        assertEquals("John", existingClient.getFirstName());
        assertEquals("Doe", existingClient.getLastName());
        assertEquals("john@example.com", existingClient.getEmail());
        assertEquals("newPassword", existingClient.getPassword());
    }

    @Test
    void updateClient_InvalidUser() {

        UpdateClientRequest request = new UpdateClientRequest(2L, "Jane", "Doe", "jane@example.com", "newPassword");
        when(clientRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(InvalidUserException.class, () -> updateClientUseCase.updateClient(request));
        verify(clientRepository, times(1)).findById(2L);
        verify(clientRepository, never()).save(any(ClientEntity.class));
    }
}
