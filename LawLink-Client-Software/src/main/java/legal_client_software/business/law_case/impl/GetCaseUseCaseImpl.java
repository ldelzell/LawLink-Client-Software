package legal_client_software.business.law_case.impl;

import legal_client_software.business.law_case.GetCaseUseCase;
import legal_client_software.domain.law_case.Case;
import legal_client_software.persistence.entity.ClientEntity;
import legal_client_software.persistence.entity.CaseEntity;
import legal_client_software.persistence.repository.CaseRepository;
import legal_client_software.persistence.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetCaseUseCaseImpl implements GetCaseUseCase {
    private final CaseRepository caseRepository;
    private final ClientRepository clientRepository;

    @Override
    public Optional<Case> getCaseById(long caseId) {
        Optional<CaseEntity> caseOptional = caseRepository.findById(caseId);

        if (caseOptional.isPresent()) {
            CaseEntity caseEntity = caseOptional.get();
            Case case1 = CaseConverter.convert(caseEntity);
            return Optional.of(case1);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Case> getCaseByUserId(long clientId) {
        Optional<ClientEntity> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isPresent()) {
            ClientEntity client = clientOptional.get();
            Long caseId = client.getCaseId();
            if (caseId != null) {
                Optional<CaseEntity> caseEntityOptional = caseRepository.findById(caseId);

                if (caseEntityOptional.isPresent()) {
                    CaseEntity caseEntity = caseEntityOptional.get();
                    return Optional.of(CaseConverter.convert(caseEntity));
                }
            }
        }
        return Optional.empty();
    }
}