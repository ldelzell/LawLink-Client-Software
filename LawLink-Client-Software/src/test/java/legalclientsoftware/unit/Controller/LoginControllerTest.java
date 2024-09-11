package legalclientsoftware.unit.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import legal_client_software.business.LoginUseCase;
import legal_client_software.business.impl.RefreshTokenUseCaseImpl;
import legal_client_software.controller.LoginController;
import legal_client_software.domain.LoginRequest;
import legal_client_software.domain.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class LoginControllerTest {

    private LoginUseCase loginUseCase;
    private LoginController loginController;
    private RefreshTokenUseCaseImpl refreshTokenUseCase;

    @BeforeEach
    void setUp() {
        loginUseCase = mock(LoginUseCase.class);
        refreshTokenUseCase = mock(RefreshTokenUseCaseImpl.class);
        loginController = new LoginController(loginUseCase, refreshTokenUseCase);
    }

    @Test
    void login_ValidRequest_ReturnsCreated() {

        LoginRequest loginRequest = new LoginRequest();
        LoginResponse loginResponse = new LoginResponse();
        when(loginUseCase.login(loginRequest)).thenReturn(loginResponse);

        ResponseEntity<LoginResponse> result = loginController.login(loginRequest);

        assertSame(loginResponse, result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }
}
