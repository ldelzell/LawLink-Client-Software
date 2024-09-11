package legal_client_software.persistence.repository;

import legal_client_software.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    void deleteById(long userId);
    UserEntity findByClientId(long clientId);
    long getClientIdById(long userId);

}
