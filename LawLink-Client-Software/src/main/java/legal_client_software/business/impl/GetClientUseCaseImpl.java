package legal_client_software.business.impl;

import legal_client_software.business.GetClientUseCase;
import legal_client_software.business.exception.UnauthorizedDataAccessException;
import legal_client_software.configuration.security.token.AccessToken;
import legal_client_software.domain.Client;
import legal_client_software.persistence.entity.ClientEntity;
import legal_client_software.persistence.entity.Role;
import legal_client_software.persistence.entity.UserEntity;
import legal_client_software.persistence.repository.ClientRepository;
import legal_client_software.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

    @Service
    @AllArgsConstructor
public class GetClientUseCaseImpl implements GetClientUseCase {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private AccessToken requestAccessToken;

        @Override
        public Optional<Client> getClientByUserId(long userId) {
            if (!requestAccessToken.hasRole(Role.ATTORNEY.name()) && requestAccessToken.getUserId() != userId) {
                throw new UnauthorizedDataAccessException("USER_ID_NOT_FROM_LOGGED_IN_USER");
            }

            Optional<UserEntity> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                UserEntity user = userOptional.get();
                ClientEntity client = user.getClient();
                if (client != null) {
                    return Optional.of(ClientConverter.convert(client));
                }
            }
            return Optional.empty();
        }




        @Override
        public Optional<Client> getClient(long clientId) {

            if (!requestAccessToken.hasRole(Role.ATTORNEY.name())) {
                throw new UnauthorizedDataAccessException("ACCESS_DENIED");
            }
            Optional<ClientEntity> clientOptional = clientRepository.findById(clientId);

            if (clientOptional.isPresent()) {
                ClientEntity clientEntity = clientOptional.get();
                Client client = ClientConverter.convert(clientEntity);
                return Optional.of(client);
            }

            return Optional.empty();
        }


}
