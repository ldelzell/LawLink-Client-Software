package legal_client_software.business.law_case;

import legal_client_software.domain.law_case.CreateCaseRequest;
import legal_client_software.domain.law_case.CreateCaseResponse;

public interface CreateCaseUseCase {
    CreateCaseResponse createCase(CreateCaseRequest request);
}
