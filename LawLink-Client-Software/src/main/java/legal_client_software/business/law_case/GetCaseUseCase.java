package legal_client_software.business.law_case;

import legal_client_software.domain.law_case.Case;

import java.util.Optional;

public interface GetCaseUseCase {
    Optional<Case> getCaseById(long caseId);
    Optional<Case> getCaseByUserId(long userId);

}
