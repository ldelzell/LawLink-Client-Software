package legal_client_software.business.law_case;

import legal_client_software.domain.law_case.UpdateCaseRequest;

public interface UpdateCaseUseCase {
    void updateCase(UpdateCaseRequest request);
}
