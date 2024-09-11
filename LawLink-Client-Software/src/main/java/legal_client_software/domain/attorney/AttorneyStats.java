package legal_client_software.domain.attorney;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttorneyStats {
    private Long attorneyId;
    private String firstName;
    private String lastName;
    private Long totalWins;

}
