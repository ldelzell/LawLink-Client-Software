package legal_client_software.business.attorney.impl;

import legal_client_software.business.attorney.GetClientsByAttorneyIdUseCase;
import legal_client_software.domain.Client;
import legal_client_software.domain.attorney.GetClientsByAttorneyResponse;
import legal_client_software.persistence.entity.AttorneyEntity;
import legal_client_software.persistence.repository.AttorneyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetClientsByAttorneyIdUseCaseImpl implements GetClientsByAttorneyIdUseCase {

    private final AttorneyRepository attorneyRepository;

    @Override
    public GetClientsByAttorneyResponse getClientsByAttorneyId(Long attorneyId) {
        AttorneyEntity attorney = attorneyRepository.findById(attorneyId)
                .orElseThrow(() -> new RuntimeException("Attorney not found"));

        List<Client> clients = attorney.getClients().stream()
                .map(clientEntity -> Client.builder()
                        .id(clientEntity.getId())
                        .firstName(clientEntity.getFirstName())
                        .lastName(clientEntity.getLastName())
                        .email(clientEntity.getEmail())
                        .caseId(clientEntity.getCaseId())
                        .attorney(clientEntity.getAttorney())
                        .build())
                .collect(Collectors.toList());

        return GetClientsByAttorneyResponse.builder()
                .clients(clients)
                .build();
    }
}
