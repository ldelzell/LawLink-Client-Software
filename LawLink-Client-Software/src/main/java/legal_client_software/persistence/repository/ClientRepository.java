package legal_client_software.persistence.repository;

import legal_client_software.persistence.entity.ClientEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    ClientEntity save(ClientEntity client);
    ClientEntity getById(long clientId);
    @Transactional
    void deleteById(long userId);
    List<ClientEntity> findAll();
    boolean existsByEmail(String email);
    @Query("SELECT c.id FROM UserEntity u JOIN u.client c WHERE u.id = :userId")
    Long findClientIdByUserId(@Param("userId") long userId);

}
