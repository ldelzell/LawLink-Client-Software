package legal_client_software.configuration.security.token;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
