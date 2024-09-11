package legal_client_software.business.impl;

import legal_client_software.configuration.security.token.AccessTokenEncoder;
import legal_client_software.configuration.security.token.exception.InvalidAccessTokenException;
import legal_client_software.configuration.security.token.impl.AccessTokenImpl;
import legal_client_software.domain.refresh_token.RefreshTokenRequest;
import legal_client_software.domain.refresh_token.RefreshTokenResponse;
import legal_client_software.persistence.entity.RefreshTokenEntity;
import legal_client_software.persistence.entity.UserEntity;
import legal_client_software.persistence.repository.RefreshTokenRepository;
import legal_client_software.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefreshTokenUseCaseImpl {


    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final AccessTokenEncoder accessTokenEncoder;

    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        RefreshTokenEntity refreshToken = refreshTokenRepository.findByToken(requestRefreshToken)
                .orElseThrow(() -> new InvalidAccessTokenException("Refresh token is invalid!"));

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidAccessTokenException("Refresh token has expired!");
        }

        UserEntity user = refreshToken.getUser();

        String newAccessToken = accessTokenEncoder.encode(
                new AccessTokenImpl(user.getUsername(), user.getId(), getUserRoles(user))
        );

        String newRefreshToken = UUID.randomUUID().toString();
        refreshToken.setToken(newRefreshToken);
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshTokenRepository.save(refreshToken);

        return new RefreshTokenResponse(newAccessToken, newRefreshToken);
    }

    private Set<String> getUserRoles(UserEntity user) {
        return Optional.ofNullable(user.getUserRoles())
                .orElse(Collections.emptySet())
                .stream()
                .map(userRole -> userRole.getRole().toString())
                .collect(Collectors.toSet());
    }
}
