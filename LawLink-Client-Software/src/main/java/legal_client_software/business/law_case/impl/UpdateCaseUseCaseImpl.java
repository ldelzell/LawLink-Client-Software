package legal_client_software.business.law_case.impl;

import legal_client_software.business.law_case.UpdateCaseUseCase;
import legal_client_software.business.exception.InvalidCaseException;
import legal_client_software.domain.law_case.UpdateCaseRequest;
import legal_client_software.persistence.entity.CaseEntity;
import legal_client_software.persistence.repository.CaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateCaseUseCaseImpl implements UpdateCaseUseCase {
    private final CaseRepository caseRepository;

    public void updateCase(UpdateCaseRequest request) {
        Optional<CaseEntity> caseOptional = caseRepository.findById(request.getId());
        if (caseOptional.isEmpty()) {
            throw new InvalidCaseException("INVALID_CASE");
        }

        CaseEntity caseEntity = caseOptional.get();
        updateFields(request, caseEntity);
        caseRepository.save(caseEntity);
    }

    private void updateFields(UpdateCaseRequest request, CaseEntity caseEntity) {
        if (Boolean.TRUE.equals(request.getStatus()) && request.getIsCaseWon() == null) {
            throw new InvalidCaseException("Case won/lost status must be set if the case is finished.");
        }

        caseEntity.setType(request.getType());
        caseEntity.setInformation(request.getInformation());
        caseEntity.setStatus(request.getStatus() == null ? caseEntity.getStatus() : request.getStatus());
        caseEntity.setIsCaseWon(request.getIsCaseWon() == null ? caseEntity.getIsCaseWon() : request.getIsCaseWon());
    }
}
