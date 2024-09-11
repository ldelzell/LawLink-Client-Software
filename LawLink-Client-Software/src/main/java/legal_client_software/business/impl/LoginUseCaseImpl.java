package legal_client_software.business.impl;

import jakarta.transaction.Transactional;
import legal_client_software.business.LoginUseCase;
import legal_client_software.business.exception.InvalidUserException;
import legal_client_software.configuration.security.token.AccessToken;
import legal_client_software.configuration.security.token.AccessTokenEncoder;
import legal_client_software.configuration.security.token.exception.InvalidAccessTokenException;
import legal_client_software.configuration.security.token.impl.AccessTokenImpl;
import legal_client_software.domain.LoginRequest;
import legal_client_software.domain.LoginResponse;
import legal_client_software.persistence.entity.RefreshTokenEntity;
import legal_client_software.persistence.entity.UserEntity;
import legal_client_software.persistence.repository.RefreshTokenRepository;
import legal_client_software.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;

@Service
@AllArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoginUseCaseImpl.class);

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null) {
            throw new InvalidAccessTokenException("Invalid username or password");
        }

        if (!matchesPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidUserException("Invalid username or password");
        }

        String accessToken = generateAccessToken(user);
        RefreshTokenEntity refreshToken = generateRefreshToken(user);

        user.setRefreshTokenId(refreshToken);
        userRepository.save(user);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .userId(user.getId())
                .userRoles(getUserRole(user))
                .build();
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String generateAccessToken(UserEntity user) {
        logger.info("User: {}", user.getUsername());
        Long userId = user.getId();
        logger.info("User Id: {}", userId);
        Set<String> userRoles = Optional.ofNullable(user.getUserRoles())
                .orElse(Collections.emptySet())
                .stream()
                .map(userRole -> userRole.getRole().toString())
                .collect(Collectors.toSet());
        logger.info("Roles: {}", userRoles);

        return accessTokenEncoder.encode(
                new AccessTokenImpl(user.getUsername(), userId, userRoles));
    }

    private String getUserRole(UserEntity user) {
        return Optional.ofNullable(user.getUserRoles())
                .orElse(Collections.emptySet())
                .stream()
                .findFirst()
                .map(userRole -> userRole.getRole().toString())
                .orElse(null);
    }

    private RefreshTokenEntity generateRefreshToken(UserEntity user) {
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7)); // Set expiration as per your need
        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public void logout(long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));

        refreshTokenRepository.deleteByUserId(userId);

        user.setRefreshTokenId(null);
        userRepository.save(user);
    }

}