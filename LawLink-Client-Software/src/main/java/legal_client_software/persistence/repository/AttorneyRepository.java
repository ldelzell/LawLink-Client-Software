package legal_client_software.persistence.repository;

import legal_client_software.persistence.entity.AttorneyEntity;
import legal_client_software.persistence.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface AttorneyRepository extends JpaRepository<AttorneyEntity, Long> {
    ClientEntity save(ClientEntity client);
    Optional<ClientEntity> getById(long clientId);
    void deleteById(long userId);
    boolean existsByEmail(String email);

    @Query("SELECT " +
            "(SUM(CASE WHEN c.status = true AND c.isCaseWon = true THEN 1 ELSE 0 END) * 100.0 / " +
            "SUM(CASE WHEN c.status = true THEN 1 ELSE 0 END)) as winRate " +
            "FROM AttorneyEntity a " +
            "JOIN AttorneyCaseEntity ac ON a.id = ac.attorneyId " +
            "JOIN CaseEntity c ON ac.caseId = c.id " +
            "WHERE a.id = :attorneyId")
    Double getWinRateForAttorney(@Param("attorneyId") Long attorneyId);

    @Query( "SELECT a.id, a.firstName, a.lastName, " +
            "SUM(CASE WHEN c.status = true AND c.isCaseWon = true THEN 1 ELSE 0 END) AS totalWins " +
            "FROM AttorneyEntity a " +
            "JOIN AttorneyCaseEntity ac ON a.id = ac.attorneyId " +
            "LEFT JOIN CaseEntity c ON ac.caseId = c.id " +
            "GROUP BY a.id, a.firstName, a.lastName " +
            "ORDER BY totalWins DESC")
    List<Object[]>getAttorneysStats();

}