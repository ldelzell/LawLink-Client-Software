package legal_client_software.business.attorney;

import legal_client_software.domain.attorney.GetClientsByAttorneyResponse;

public interface GetClientsByAttorneyIdUseCase {
    GetClientsByAttorneyResponse getClientsByAttorneyId(Long attorneyId);
}
