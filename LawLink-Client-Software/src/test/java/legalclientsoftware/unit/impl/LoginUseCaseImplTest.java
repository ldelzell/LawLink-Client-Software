package legalclientsoftware.unit.impl;

import legal_client_software.business.exception.InvalidUserException;
import legal_client_software.business.impl.LoginUseCaseImpl;
import legal_client_software.configuration.security.token.AccessTokenEncoder;
import legal_client_software.configuration.security.token.exception.InvalidAccessTokenException;
import legal_client_software.domain.LoginRequest;
import legal_client_software.domain.LoginResponse;
import legal_client_software.persistence.entity.RefreshTokenEntity;
import legal_client_software.persistence.entity.UserEntity;
import legal_client_software.persistence.repository.RefreshTokenRepository;
import legal_client_software.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class LoginUseCaseImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccessTokenEncoder accessTokenEncoder;

    @InjectMocks
    private LoginUseCaseImpl loginUseCase;

    @BeforeEach
    public void setUp() {
        loginUseCase = new LoginUseCaseImpl(userRepository, passwordEncoder, accessTokenEncoder,refreshTokenRepository);
    }

    @Test
    void testLogin_Success() {
        LoginRequest loginRequest = new LoginRequest("username", "password");
        UserEntity user = new UserEntity();
        user.setUsername("username");
        user.setPassword("encodedPassword");

        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setToken("mockRefreshToken"); // Mock token value

        when(userRepository.findByUsername("username")).thenReturn(user);
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(refreshTokenRepository.save(any(RefreshTokenEntity.class))).thenReturn(refreshToken); // Mocking save call on refreshTokenRepository
        when(accessTokenEncoder.encode(any())).thenReturn("mockAccessToken");

        LoginResponse loginResponse = loginUseCase.login(loginRequest);

        assertNotNull(loginResponse);
        assertEquals("mockAccessToken", loginResponse.getAccessToken());
        verify(userRepository, times(1)).findByUsername("username");
        verify(passwordEncoder, times(1)).matches("password", "encodedPassword");
        verify(accessTokenEncoder, times(1)).encode(any());
        verify(refreshTokenRepository, times(1)).save(any()); // Verify save is called on refreshTokenRepository
    }



    @Test
    void testLogin_UserNotFound() {

        LoginRequest loginRequest = new LoginRequest("username", "password");

        when(userRepository.findByUsername("username")).thenReturn(null);

        assertThrows(InvalidAccessTokenException.class, () -> loginUseCase.login(loginRequest));

        verify(userRepository, times(1)).findByUsername("username");
        verifyNoMoreInteractions(passwordEncoder, accessTokenEncoder);
    }

    @Test
    void testLogin_InvalidCredentials() {

        LoginRequest loginRequest = new LoginRequest("username", "password");
        UserEntity user = new UserEntity();
        user.setUsername("username");
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername("username")).thenReturn(user);
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(false);

        assertThrows(InvalidUserException.class, () -> loginUseCase.login(loginRequest));

        verify(userRepository, times(1)).findByUsername("username");
        verify(passwordEncoder, times(1)).matches("password", "encodedPassword");
        verifyNoMoreInteractions(accessTokenEncoder);
    }
}
