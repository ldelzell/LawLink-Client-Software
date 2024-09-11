package legal_client_software.business;

import legal_client_software.domain.CreateClientRequest;
import legal_client_software.domain.CreateClientResponse;
import legal_client_software.domain.attorney.CreateAttorneyRequest;
import legal_client_software.domain.attorney.CreateAttorneyResponse;

public interface CreateClientUseCase {
    CreateClientResponse createClient(CreateClientRequest request);
    CreateAttorneyResponse createAttorney(CreateAttorneyRequest request);
}
