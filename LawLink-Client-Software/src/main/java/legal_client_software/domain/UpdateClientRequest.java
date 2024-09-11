package legal_client_software.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClientRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
