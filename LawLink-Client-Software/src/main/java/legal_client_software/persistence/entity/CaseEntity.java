package legal_client_software.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "law_case")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "starting_date")
    private Date startingDate;

    @Column(name = "information")
    private String information;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "isCaseWon", nullable = false)
    private Boolean isCaseWon;
}

