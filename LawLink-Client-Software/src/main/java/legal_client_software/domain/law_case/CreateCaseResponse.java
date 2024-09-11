package legal_client_software.domain.law_case;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateCaseResponse {
    private Long caseId;

}
