package legal_client_software.business;

import legal_client_software.domain.LoginRequest;
import legal_client_software.domain.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(LoginRequest loginRequest);
    void logout(long userId);
}
