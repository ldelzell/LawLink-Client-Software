package legalclientsoftware.unit.impl.Attorney;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import legal_client_software.business.attorney.impl.GetAttorneyUseCaseImpl;
import legal_client_software.domain.attorney.Attorney;
import legal_client_software.persistence.entity.AttorneyEntity;
import legal_client_software.persistence.entity.UserEntity;
import legal_client_software.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
 class GetAttorneyUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetAttorneyUseCaseImpl getAttorneyUseCase;

    private UserEntity userEntity;
    private AttorneyEntity attorneyEntity;

    @BeforeEach
    void setUp() {
        attorneyEntity = new AttorneyEntity();

        userEntity = new UserEntity();
        userEntity.setAttorney(attorneyEntity);
    }

    @Test
    void testGetAttorneyByUserId_UserAndAttorneyExists() {

        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        Optional<Attorney> result = getAttorneyUseCase.getAttorneyByUserId(userId);

        assertTrue(result.isPresent(), "Attorney should be present");
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetAttorneyByUserId_UserExistsButNoAttorney() {

        long userId = 1L;
        userEntity.setAttorney(null);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        Optional<Attorney> result = getAttorneyUseCase.getAttorneyByUserId(userId);

        assertFalse(result.isPresent(), "Attorney should not be present");
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetAttorneyByUserId_UserDoesNotExist() {

        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<Attorney> result = getAttorneyUseCase.getAttorneyByUserId(userId);

        assertFalse(result.isPresent(), "Attorney should not be present");
        verify(userRepository, times(1)).findById(userId);
    }
}
