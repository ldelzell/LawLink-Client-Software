package legal_client_software.business.attorney.impl;

import legal_client_software.business.attorney.GetAttorneyByClient;
import legal_client_software.domain.attorney.Attorney;
import legal_client_software.persistence.entity.AttorneyEntity;
import legal_client_software.persistence.entity.ClientEntity;
import legal_client_software.persistence.entity.UserEntity;
import legal_client_software.persistence.repository.ClientRepository;
import legal_client_software.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class GetAttorneyByClientImpl implements GetAttorneyByClient {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    @Override
    public Optional<Attorney> getAttorneyByClient(long userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            long clientId = user.getClient().getId(); 

            Optional<ClientEntity> clientOptional = clientRepository.findById(clientId);
            if (clientOptional.isPresent()) {
                ClientEntity client = clientOptional.get();
                AttorneyEntity attorney = client.getAttorney();
                if (attorney != null) {
                    return Optional.of(AttorneyConverter.convert(attorney));
                }
            }
        }
        return Optional.empty();
    }
}
