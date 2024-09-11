package legal_client_software.business.law_case.impl;

import legal_client_software.business.law_case.GetAllCasesPerAttorney;
import legal_client_software.domain.law_case.Case;
import legal_client_software.domain.law_case.GetAllCasesPerAttorneyResponse;
import legal_client_software.persistence.entity.AttorneyCaseEntity;
import legal_client_software.persistence.entity.CaseEntity;
import legal_client_software.persistence.repository.AttorneyCaseRepository;
import legal_client_software.persistence.repository.CaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetAllCasesPerAttorneyImpl implements GetAllCasesPerAttorney {
    private final AttorneyCaseRepository attorneyCaseRepository;
    private final CaseRepository caseRepository;

    @Override
    public GetAllCasesPerAttorneyResponse getAllCasesByAttorneyId(Long attorneyId) {
        List<Long> caseIds = attorneyCaseRepository.findAllByAttorneyId(attorneyId)
                .stream()
                .map(AttorneyCaseEntity::getCaseId)
                .collect(Collectors.toList());

        List<Case> cases = caseRepository.findAllById(caseIds)
                .stream()
                .map(this::convertToCase)
                .collect(Collectors.toList());

        return new GetAllCasesPerAttorneyResponse(cases);
    }

    private Case convertToCase(CaseEntity caseEntity) {
        return Case.builder()
                .id(caseEntity.getId())
                .type(caseEntity.getType())
                .startingDate(caseEntity.getStartingDate())
                .information(caseEntity.getInformation())
                .status(caseEntity.getStatus())
                .isCaseWon(caseEntity.getIsCaseWon())
                .build();
    }
}
