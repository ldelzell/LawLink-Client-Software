package legal_client_software.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    private UserEntity user;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name="expiry_Date",nullable = false)
    private LocalDateTime expiryDate;
}
