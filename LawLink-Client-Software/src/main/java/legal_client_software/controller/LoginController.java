package legal_client_software.controller;

import jakarta.annotation.security.RolesAllowed;
import legal_client_software.business.LoginUseCase;
import legal_client_software.business.impl.RefreshTokenUseCaseImpl;
import legal_client_software.domain.LoginRequest;
import legal_client_software.domain.LoginResponse;
import jakarta.validation.Valid;
import legal_client_software.domain.refresh_token.RefreshTokenRequest;
import legal_client_software.domain.refresh_token.RefreshTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tokens")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class LoginController {

    private final LoginUseCase loginUseCase;
    private final RefreshTokenUseCaseImpl refreshTokenUseCase;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse loginResponse = loginUseCase.login(loginRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);

    }
    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        RefreshTokenResponse tokenRefreshResponse = refreshTokenUseCase.refreshToken(request);
        return ResponseEntity.ok(tokenRefreshResponse);
    }
    @RolesAllowed({"CLIENT","ATTORNEY", "ADMIN"})
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> logout(@PathVariable Long userId) {
        loginUseCase.logout(userId);
        return ResponseEntity.noContent().build();
    }

}
