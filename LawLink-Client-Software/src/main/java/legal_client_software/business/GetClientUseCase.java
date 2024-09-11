package legal_client_software.business;

import legal_client_software.domain.Client;

import java.util.Optional;


public interface GetClientUseCase {
    Optional<Client> getClientByUserId(long clientId);

    Optional<Client> getClient(long clientId);
}
