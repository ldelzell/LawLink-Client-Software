package legal_client_software.business.attorney;

import legal_client_software.domain.attorney.UpdateAttorneyRequest;

public interface UpdateAttorneyUseCase {
    void updateAttorney(UpdateAttorneyRequest request);
}
