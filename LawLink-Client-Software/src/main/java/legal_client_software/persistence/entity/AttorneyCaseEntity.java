package legal_client_software.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attorney_case")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttorneyCaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "attorney_id")
    private Long attorneyId;

    @Column(name = "case_id")
    private Long caseId;

    public AttorneyCaseEntity(Long c1, Long attorneyId) {
    }
}
