package legal_client_software.persistence.repository;

import legal_client_software.persistence.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    void deleteByUserId(long userId);
    void deleteAll();
    Optional<UserRoleEntity> findByUserId(Long userId);

}
