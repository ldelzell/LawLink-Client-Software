package legalclientsoftware.unit.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import legal_client_software.business.exception.EmailAlreadyExistsException;
import legal_client_software.business.impl.CreateClientUseCaseImpl;
import legal_client_software.domain.CreateClientRequest;
import legal_client_software.domain.attorney.CreateAttorneyRequest;
import legal_client_software.domain.attorney.CreateAttorneyResponse;
import legal_client_software.persistence.entity.AttorneyEntity;
import legal_client_software.persistence.repository.AttorneyRepository;
import legal_client_software.persistence.repository.ClientRepository;
import legal_client_software.persistence.repository.UserRepository;
import legal_client_software.persistence.repository.UserRoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

class CreateClientUseCaseImplTest {

    private CreateClientUseCaseImpl createClientUseCase;
    private ClientRepository clientRepository;
    private AttorneyRepository attorneyRepository;
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        attorneyRepository = mock(AttorneyRepository.class);
        userRepository = mock(UserRepository.class);
        userRoleRepository = mock(UserRoleRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        createClientUseCase = new CreateClientUseCaseImpl(clientRepository, attorneyRepository,
                userRepository, passwordEncoder, userRoleRepository);
    }

    @Test
    void createClient_EmailExists_ThrowsEmailAlreadyExistsException() {

        CreateClientRequest request = new CreateClientRequest("John", "Doe", "john@example.com", "password", 1L, 1L, "CLIENT");
        when(clientRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> createClientUseCase.createClient(request));
    }

    @Test
    void createClient_AttorneyNotFound_ThrowsEntityNotFoundException() {

        CreateClientRequest request = new CreateClientRequest("John", "Doe", "john@example.com", "password", 1L, 1L, "CLIENT");
        when(clientRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(attorneyRepository.findById(request.getAttorneyId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> createClientUseCase.createClient(request));
    }
    @Test
     void createAttorney_ValidRequest_ReturnsCreateAttorneyResponse() {
        CreateAttorneyRequest request = new CreateAttorneyRequest("John", "Doe", "john@example.com", "password", "ATTORNEY");
        AttorneyEntity savedAttorney = AttorneyEntity.builder().id(1L).firstName(request.getFirstName()).lastName(request.getLastName()).email(request.getEmail()).password(request.getPassword()).build();
        CreateAttorneyResponse expectedResponse = CreateAttorneyResponse.builder().attorneyId(savedAttorney.getId()).build();

        when(attorneyRepository.existsByEmail(anyString())).thenReturn(false);
        when(attorneyRepository.save(any(AttorneyEntity.class))).thenReturn(savedAttorney);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        CreateAttorneyResponse response = createClientUseCase.createAttorney(request);

        assertNotNull(response);
        assertEquals(expectedResponse.getAttorneyId(), response.getAttorneyId());
    }

    @Test
     void createAttorney_DuplicateEmail_ThrowsEmailAlreadyExistsException() {
        CreateAttorneyRequest request = new CreateAttorneyRequest("John", "Doe", "john@example.com", "password", "ATTORNEY");

        when(attorneyRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> createClientUseCase.createAttorney(request));
    }

}

