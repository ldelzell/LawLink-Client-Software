package legal_client_software.persistence.repository;

import legal_client_software.persistence.entity.CaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CaseRepository extends JpaRepository<CaseEntity, Long> {
    CaseEntity save(CaseEntity caseEntity);
    Optional<CaseEntity> getById(long caseId);

}
