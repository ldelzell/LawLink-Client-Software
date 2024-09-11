package legalclientsoftware.unit.impl.Attorney;
import legal_client_software.business.attorney.impl.GetAttorneyByClientImpl;
import legal_client_software.domain.attorney.Attorney;
import legal_client_software.persistence.entity.ClientEntity;
import legal_client_software.persistence.entity.UserEntity;
import legal_client_software.persistence.repository.ClientRepository;
import legal_client_software.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 class GetAttorneyByClientTest {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetAttorneyByClientImpl getAttorneyByClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testGetAttorneyByClient_UserNotFound() {

        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<Attorney> result = getAttorneyByClient.getAttorneyByClient(userId);

        assertFalse(result.isPresent());
    }

    @Test
     void testGetAttorneyByClient_ClientNotFound() {

        long userId = 1L;
        UserEntity user = mock(UserEntity.class);
        ClientEntity client = mock(ClientEntity.class);
        when(user.getClient()).thenReturn(client);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(clientRepository.findById(client.getId())).thenReturn(Optional.empty());

        Optional<Attorney> result = getAttorneyByClient.getAttorneyByClient(userId);

        assertFalse(result.isPresent());
    }

    @Test
     void testGetAttorneyByClient_AttorneyNotFound() {

        long userId = 1L;
        UserEntity user = mock(UserEntity.class);
        ClientEntity client = mock(ClientEntity.class);
        when(user.getClient()).thenReturn(client);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(client.getAttorney()).thenReturn(null);

        Optional<Attorney> result = getAttorneyByClient.getAttorneyByClient(userId);

        assertFalse(result.isPresent());
    }

}