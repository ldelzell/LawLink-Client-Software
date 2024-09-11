package legal_client_software.persistence.repository;

import legal_client_software.persistence.entity.AttorneyCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttorneyCaseRepository extends JpaRepository<AttorneyCaseEntity, Long> {
    List<AttorneyCaseEntity> findAllByAttorneyId(long attorneyId);
}
