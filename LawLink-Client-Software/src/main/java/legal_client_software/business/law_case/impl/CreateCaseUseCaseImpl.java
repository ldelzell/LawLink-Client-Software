package legal_client_software.business.law_case.impl;

import jakarta.transaction.Transactional;
import legal_client_software.business.impl.EmailUseCaseImpl;
import legal_client_software.business.law_case.CreateCaseUseCase;
import legal_client_software.domain.Client;
import legal_client_software.domain.law_case.CreateCaseRequest;
import legal_client_software.domain.law_case.CreateCaseResponse;
import legal_client_software.persistence.entity.AttorneyCaseEntity;
import legal_client_software.persistence.entity.CaseEntity;
import legal_client_software.persistence.entity.ClientEntity;
import legal_client_software.persistence.repository.AttorneyCaseRepository;
import legal_client_software.persistence.repository.CaseRepository;
import legal_client_software.persistence.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateCaseUseCaseImpl implements CreateCaseUseCase {
    private  CaseRepository caseRepository;
    private  ClientRepository clientRepository;
    private  AttorneyCaseRepository attorneyCaseRepository;
    private  EmailUseCaseImpl emailUseCase;

    @Transactional
    public CreateCaseResponse createCase(CreateCaseRequest request) {
        CaseEntity savedCase = saveNewCase(request);

        updateClientWithCaseId(savedCase.getId(), request.getClientId());
        associateAttorneyWithCase(savedCase.getId(), request.getAttorneyId());

        // Send an email after a successful case assignment
        long clientId = request.getClientId();
        ClientEntity client = clientRepository.getById(clientId);
        String email = client.getEmail();
        sendVerificationEmail(email);
        return CreateCaseResponse.builder()
                .caseId(savedCase.getId())
                .build();
    }

    private CaseEntity saveNewCase(CreateCaseRequest request) {
        CaseEntity newCase = CaseEntity.builder()
                .type(request.getType())
                .startingDate(request.getStartingDate())
                .information(request.getInformation())
                .status(request.getStatus())
                .isCaseWon(request.getIsCaseWon())
                .build();
        return caseRepository.save(newCase);
    }

    private void updateClientWithCaseId(Long caseId, Long clientId) {
        Optional<ClientEntity> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isPresent()) {
            ClientEntity client = optionalClient.get();
            client.setCaseId(caseId); // Ensure this field is set
            clientRepository.save(client);
        } else {
            throw new EntityNotFoundException("Client not found with ID: " + clientId);
        }
    }

    private void associateAttorneyWithCase(Long caseId, Long attorneyId){
        AttorneyCaseEntity attorneyCase = AttorneyCaseEntity.builder()
                .attorneyId(attorneyId)
                .caseId(caseId)
                .build();
        attorneyCaseRepository.save(attorneyCase);
    }
    private void sendVerificationEmail(String email) {
        String subject = "New Case! Go Check it out...";
        String body = "You have been just assigned with your current case. Plase go inside the client software to check the details!" + email;
        emailUseCase.sendSimpleEmail(email, subject, body);
    }
}
