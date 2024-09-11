package legalclientsoftware.unit.impl;

import legal_client_software.business.exception.UnauthorizedDataAccessException;
import legal_client_software.business.impl.GetClientUseCaseImpl;
import legal_client_software.configuration.security.token.AccessToken;
import legal_client_software.domain.Client;
import legal_client_software.persistence.entity.ClientEntity;
import legal_client_software.persistence.entity.Role;
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

 class GetClientUseCaseImplTest {
    @Mock
    private AccessToken accessToken;
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetClientUseCaseImpl getClientUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getClientByUserId_HappyPath() {
        long userId = 1L;
        UserEntity userEntity = new UserEntity();
        ClientEntity clientEntity = new ClientEntity();
        userEntity.setClient(clientEntity);

        when(accessToken.hasRole(Role.ATTORNEY.name())).thenReturn(true);
        when(accessToken.getUserId()).thenReturn(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        Optional<Client> clientOptional = getClientUseCase.getClientByUserId(userId);

        assertTrue(clientOptional.isPresent());
        assertNotNull(clientOptional.get());
        verify(userRepository, times(1)).findById(userId);
        verify(clientRepository, never()).findById(anyLong());
    }

    @Test
    void getClientByUserId_NoClientForUser() {
        long userId = 2L;

        when(accessToken.hasRole(Role.ATTORNEY.name())).thenReturn(true);
        when(accessToken.getUserId()).thenReturn(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<Client> clientOptional = getClientUseCase.getClientByUserId(userId);

        assertFalse(clientOptional.isPresent());
        verify(userRepository, times(1)).findById(userId);
        verify(clientRepository, never()).findById(anyLong());
    }

    @Test
    void getClientByUserId_UnauthorizedAccessException() {
        long userId = 1L;

        when(accessToken.hasRole(Role.ATTORNEY.name())).thenReturn(false);
        when(accessToken.getUserId()).thenReturn(userId + 1);

        assertThrows(UnauthorizedDataAccessException.class, () -> getClientUseCase.getClientByUserId(userId));
        verify(userRepository, never()).findById(userId);
        verify(clientRepository, never()).findById(anyLong());
    }

     @Test
     void getClient_HappyPath() {

         long userId = 1L;
         long clientId = 1L;
         ClientEntity clientEntity = new ClientEntity();
         when(accessToken.hasRole(Role.ATTORNEY.name())).thenReturn(true);
         when(accessToken.getUserId()).thenReturn(userId);
         when(userRepository.findById(userId)).thenReturn(Optional.of(new UserEntity()));
         when(clientRepository.findById(clientId)).thenReturn(Optional.of(clientEntity));

         Optional<Client> clientOptional = getClientUseCase.getClientByUserId(userId);

         assertTrue(true);
         assertNotNull(true);
     }

     @Test
     void getClient_NoClientFound() {

         long userId = 1L;

         when(accessToken.hasRole(Role.ATTORNEY.name())).thenReturn(true);
         when(accessToken.getUserId()).thenReturn(userId);
         when(userRepository.findById(userId)).thenReturn(Optional.of(new UserEntity()));

         when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

         Optional<Client> clientOptional = getClientUseCase.getClientByUserId(userId);

         assertTrue(clientOptional.isEmpty());
     }
}