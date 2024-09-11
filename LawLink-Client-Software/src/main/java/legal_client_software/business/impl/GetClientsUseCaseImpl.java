package legal_client_software.business.impl;

import legal_client_software.business.GetClientsUseCase;
import legal_client_software.domain.GetClientsResponse;
import legal_client_software.domain.Client;
import legal_client_software.persistence.entity.ClientEntity;
import legal_client_software.persistence.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class GetClientsUseCaseImpl implements GetClientsUseCase {
    private final ClientRepository clientRepository;
    @Override
    public GetClientsResponse getClients(){
        List<ClientEntity> results;

        results = clientRepository.findAll();

        final GetClientsResponse response = new GetClientsResponse();

        List<Client> clients = results
                .stream()
                .map(ClientConverter::convert)
                .toList();
        response.setClients(clients);

        return response;
    }
}
