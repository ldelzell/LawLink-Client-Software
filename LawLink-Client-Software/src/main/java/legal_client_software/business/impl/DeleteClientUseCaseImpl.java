package legal_client_software.business.impl;

import legal_client_software.business.DeleteClientUseCase;
import legal_client_software.persistence.entity.Role;
import legal_client_software.persistence.entity.UserRoleEntity;
import legal_client_software.persistence.repository.AttorneyRepository;
import legal_client_software.persistence.repository.ClientRepository;
import legal_client_software.persistence.repository.UserRepository;
import legal_client_software.persistence.repository.UserRoleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteClientUseCaseImpl implements DeleteClientUseCase {
    private final ClientRepository clientRepository;
    private final AttorneyRepository attorneyRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    @Transactional
    public void deleteClient(long userId) {
        Optional<UserRoleEntity> userRoleOptional = userRoleRepository.findByUserId(userId);

        if (userRoleOptional.isPresent()) {
            UserRoleEntity userRoleEntity = userRoleOptional.get();
            Role role = userRoleEntity.getRole();

            if (Role.CLIENT.equals(role)) {
                clientRepository.deleteById(userId);
            } else if (Role.ATTORNEY.equals(role)) {
                attorneyRepository.deleteById(userId);
            }

            userRoleRepository.deleteByUserId(userId);

            userRepository.deleteById(userId);
        } else {
            throw new IllegalArgumentException("User role not found for userId: " + userId);
        }
    }
}


