package legal_client_software.business.impl;

import legal_client_software.business.CreateClientUseCase;
import legal_client_software.business.exception.EmailAlreadyExistsException;
import legal_client_software.domain.CreateClientRequest;
import legal_client_software.domain.CreateClientResponse;
import legal_client_software.domain.attorney.CreateAttorneyRequest;
import legal_client_software.domain.attorney.CreateAttorneyResponse;
import legal_client_software.persistence.entity.*;
import legal_client_software.persistence.repository.AttorneyRepository;
import legal_client_software.persistence.repository.ClientRepository;
import legal_client_software.persistence.repository.UserRepository;
import legal_client_software.persistence.repository.UserRoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class CreateClientUseCaseImpl implements CreateClientUseCase {

    private final ClientRepository clientRepository;
    private final AttorneyRepository attorneyRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;

    @Override
    public CreateClientResponse createClient(CreateClientRequest request) {
        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        AttorneyEntity attorney = attorneyRepository.findById(request.getAttorneyId())
                .orElseThrow(() -> new EntityNotFoundException("Attorney not found"));

        ClientEntity savedClient = saveNewClient(request, attorney);
        saveNewUser(savedClient, null, request.getPassword(), request.getRole());

        return CreateClientResponse.builder()
                .clientId(savedClient.getId())
                .build();
    }


    @Override
    public CreateAttorneyResponse createAttorney(CreateAttorneyRequest request) {
        if (attorneyRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException();
        }
        AttorneyEntity savedAttorney = saveNewAttorney(request);

        saveNewUser(null, savedAttorney, request.getPassword(), request.getRole());

        return CreateAttorneyResponse.builder()
                .attorneyId((savedAttorney.getId()))
                .build();
    }

    private void saveNewUser(ClientEntity client, AttorneyEntity attorney, String password, String role) {
        String encodedPassword = passwordEncoder.encode(password);

        UserEntity newUser = UserEntity.builder()
                .username((client != null ? client.getEmail() : attorney.getEmail()))
                .password(encodedPassword)
                .client(client)
                .attorney(attorney)
                .build();

        Role assignedRole = Role.valueOf(role.toUpperCase());
        UserRoleEntity userRole = UserRoleEntity.builder()
                .user(newUser)
                .role(assignedRole)
                .build();
        newUser.setUserRoles(Set.of(userRole));
        userRepository.save(newUser);
        userRoleRepository.save(userRole);
    }

    private ClientEntity saveNewClient(CreateClientRequest request, AttorneyEntity attorney) {
        ClientEntity newClient = ClientEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .attorney(attorney)
                .build();
        return clientRepository.save(newClient);
    }

    private AttorneyEntity saveNewAttorney(CreateAttorneyRequest request) {
        AttorneyEntity newAttorney = AttorneyEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        return attorneyRepository.save(newAttorney);
    }
}
