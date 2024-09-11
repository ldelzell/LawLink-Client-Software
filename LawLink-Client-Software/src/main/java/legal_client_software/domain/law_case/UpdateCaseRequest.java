package legal_client_software.domain.law_case;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCaseRequest {
    private Long id;
    private String type;
    private String information;
    private Boolean status;
    private Boolean isCaseWon;
}
