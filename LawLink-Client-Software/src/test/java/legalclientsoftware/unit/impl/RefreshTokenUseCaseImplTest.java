package legalclientsoftware.unit.impl;
import legal_client_software.business.impl.RefreshTokenUseCaseImpl;
import legal_client_software.configuration.security.token.AccessTokenEncoder;
import legal_client_software.configuration.security.token.exception.InvalidAccessTokenException;
import legal_client_software.configuration.security.token.impl.AccessTokenImpl;
import legal_client_software.domain.refresh_token.RefreshTokenRequest;
import legal_client_software.domain.refresh_token.RefreshTokenResponse;
import legal_client_software.persistence.entity.RefreshTokenEntity;
import legal_client_software.persistence.entity.UserEntity;
import legal_client_software.persistence.repository.RefreshTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenUseCaseImplTest {




    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private AccessTokenEncoder accessTokenEncoder;

    @InjectMocks
    private RefreshTokenUseCaseImpl refreshTokenService;

    private RefreshTokenRequest refreshTokenRequest;
    private RefreshTokenEntity refreshTokenEntity;
    private UserEntity userEntity;

    @BeforeEach
    public void setup() {
        refreshTokenRequest = new RefreshTokenRequest("valid-refresh-token");
        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("testuser");

        refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setToken("valid-refresh-token");
        refreshTokenEntity.setExpiryDate(LocalDateTime.now().plusDays(1));
        refreshTokenEntity.setUser(userEntity);
    }

    @Test
    public void refreshToken_ShouldReturnNewTokens_WhenRefreshTokenIsValid() {
        when(refreshTokenRepository.findByToken(anyString())).thenReturn(Optional.of(refreshTokenEntity));
        when(accessTokenEncoder.encode(any(AccessTokenImpl.class))).thenReturn("new-access-token");

        RefreshTokenResponse response = refreshTokenService.refreshToken(refreshTokenRequest);

        assertNotNull(response);
        assertEquals("new-access-token", response.getAccessToken());
        assertNotEquals("valid-refresh-token", response.getRefreshToken());
        assertTrue(response.getRefreshToken().matches("[0-9a-fA-F-]{36}"));

        verify(refreshTokenRepository, times(1)).findByToken(anyString());
        verify(accessTokenEncoder, times(1)).encode(any(AccessTokenImpl.class));
        verify(refreshTokenRepository, times(1)).save(any(RefreshTokenEntity.class));
    }

    @Test
    public void refreshToken_ShouldThrowException_WhenRefreshTokenIsInvalid() {
        when(refreshTokenRepository.findByToken(anyString())).thenReturn(Optional.empty());

        InvalidAccessTokenException exception = assertThrows(InvalidAccessTokenException.class, () -> {
            refreshTokenService.refreshToken(refreshTokenRequest);
        });

        assertEquals("401 UNAUTHORIZED \"Refresh token is invalid!\"", exception.getMessage());

        verify(refreshTokenRepository, times(1)).findByToken(anyString());
        verify(accessTokenEncoder, times(0)).encode(any(AccessTokenImpl.class));
        verify(refreshTokenRepository, times(0)).save(any(RefreshTokenEntity.class));
    }

    @Test
    public void refreshToken_ShouldThrowException_WhenRefreshTokenHasExpired() {
        refreshTokenEntity.setExpiryDate(LocalDateTime.now().minusDays(1));
        when(refreshTokenRepository.findByToken(anyString())).thenReturn(Optional.of(refreshTokenEntity));

        InvalidAccessTokenException exception = assertThrows(InvalidAccessTokenException.class, () -> {
            refreshTokenService.refreshToken(refreshTokenRequest);
        });

        assertEquals("401 UNAUTHORIZED \"Refresh token has expired!\"", exception.getMessage());

        verify(refreshTokenRepository, times(1)).findByToken(anyString());
        verify(accessTokenEncoder, times(0)).encode(any(AccessTokenImpl.class));
        verify(refreshTokenRepository, times(0)).save(any(RefreshTokenEntity.class));
    }
}
