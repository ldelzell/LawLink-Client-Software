package legal_client_software.business.law_case;

import legal_client_software.domain.law_case.GetAllCasesPerAttorneyResponse;

public interface GetAllCasesPerAttorney {
    GetAllCasesPerAttorneyResponse getAllCasesByAttorneyId(Long attorneyId);

}
