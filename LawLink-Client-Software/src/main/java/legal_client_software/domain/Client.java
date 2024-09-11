package legal_client_software.domain;

import legal_client_software.persistence.entity.AttorneyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long caseId;
    private AttorneyEntity attorney;

    public Client(String stas, String nikolov, String mail, String stas1) {
    }
}
