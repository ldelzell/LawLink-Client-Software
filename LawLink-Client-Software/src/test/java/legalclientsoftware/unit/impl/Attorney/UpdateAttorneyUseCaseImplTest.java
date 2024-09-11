package legalclientsoftware.unit.impl.Attorney;

import legal_client_software.business.attorney.impl.UpdateAttorneyUseCaseImpl;
import legal_client_software.business.exception.InvalidUserException;
import legal_client_software.domain.attorney.UpdateAttorneyRequest;
import legal_client_software.persistence.entity.AttorneyEntity;
import legal_client_software.persistence.entity.ClientEntity;
import legal_client_software.persistence.repository.AttorneyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class UpdateAttorneyUseCaseImplTest {

    @Mock
    private AttorneyRepository attorneyRepository;

    @InjectMocks
    private UpdateAttorneyUseCaseImpl updateAttorneyUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testUpdateAttorney_AttorneyFound() {

        UpdateAttorneyRequest request = new UpdateAttorneyRequest();
        request.setId(1L);
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@example.com");
        request.setPassword("password");

        AttorneyEntity attorneyEntity = new AttorneyEntity();
        when(attorneyRepository.findById(request.getId())).thenReturn(Optional.of(attorneyEntity));

        assertDoesNotThrow(() -> updateAttorneyUseCase.updateAttorney(request));
        verify(attorneyRepository, times(1)).save(attorneyEntity);
        assertEquals("John", attorneyEntity.getFirstName());
        assertEquals("Doe", attorneyEntity.getLastName());
        assertEquals("john@example.com", attorneyEntity.getEmail());
        assertEquals("password", attorneyEntity.getPassword());
    }

    @Test
     void testUpdateAttorney_AttorneyNotFound() {

        UpdateAttorneyRequest request = new UpdateAttorneyRequest();
        request.setId(1L);

        when(attorneyRepository.findById(request.getId())).thenReturn(Optional.empty());

        assertThrows(InvalidUserException.class, () -> updateAttorneyUseCase.updateAttorney(request));
        verify(attorneyRepository, never()).save((ClientEntity) any());
    }
}
