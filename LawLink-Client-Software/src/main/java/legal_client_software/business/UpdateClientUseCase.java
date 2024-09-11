package legal_client_software.business;

import legal_client_software.domain.UpdateClientRequest;

public interface UpdateClientUseCase {
    void updateClient(UpdateClientRequest request);
}
