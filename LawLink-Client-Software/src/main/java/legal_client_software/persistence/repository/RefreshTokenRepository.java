package legal_client_software.persistence.repository;

import legal_client_software.persistence.entity.RefreshTokenEntity;
import legal_client_software.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);
    void deleteByUser(UserEntity user);
    void deleteByUserId(Long userId);
}
