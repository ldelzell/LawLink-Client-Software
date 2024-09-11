package legal_client_software.business.impl;

import legal_client_software.business.UpdateClientUseCase;
import legal_client_software.business.exception.InvalidUserException;
import legal_client_software.domain.UpdateClientRequest;
import legal_client_software.persistence.entity.ClientEntity;
import legal_client_software.persistence.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateClientUseCaseImpl implements UpdateClientUseCase {
    private final ClientRepository clientRepository;

    @Override
    public void updateClient(UpdateClientRequest request) {
        Optional<ClientEntity> clientOptional = clientRepository.findById(request.getId());
        if (clientOptional.isEmpty()) {
            throw new InvalidUserException("INVALID_USER");
        }

        ClientEntity client = clientOptional.get();
        updateFields(request, client);
    }

    private void updateFields(UpdateClientRequest request, ClientEntity client) {
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setEmail(request.getEmail());
        client.setPassword(request.getPassword());
        clientRepository.save(client);
    }
}
