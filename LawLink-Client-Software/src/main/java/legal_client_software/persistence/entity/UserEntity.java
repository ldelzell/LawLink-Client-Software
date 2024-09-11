package legal_client_software.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column (name = "id")
    private Long id;

    @NotBlank
    @Column (name = "username")
    private String username;

    @NotBlank
    @Column (name = "password")
    private String password;

    @OneToOne(optional = true)
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @OneToOne(optional = true)
    @JoinColumn(name = "attorney_id")
    private AttorneyEntity attorney;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Set<UserRoleEntity> userRoles;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "refresh_token_id")
    @ToString.Exclude // Exclude this from toString to avoid circular reference
    private RefreshTokenEntity refreshTokenId;
}
