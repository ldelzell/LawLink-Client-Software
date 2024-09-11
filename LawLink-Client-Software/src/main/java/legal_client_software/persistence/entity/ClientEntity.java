package legal_client_software.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "client")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "case_id")
    private Long caseId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "attorney_id")
    @JsonBackReference
    private AttorneyEntity attorney;

    public ClientEntity(long clientId, long caseId) {
    }

    public ClientEntity(long clientId, Long o) {
        // This constructor is intentionally left empty
    }


    @Override
    public String toString() {
        return "ClientEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", caseType='" + caseId + '\'' +
                ", attorney=" + attorney+
                '}';
    }
}