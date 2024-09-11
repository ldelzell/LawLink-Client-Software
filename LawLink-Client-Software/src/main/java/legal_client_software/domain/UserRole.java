package legal_client_software.domain;

import legal_client_software.persistence.entity.Role;
import legal_client_software.persistence.entity.UserEntity;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {
    private Long id;
    private Role role;
    private UserEntity user;
}
