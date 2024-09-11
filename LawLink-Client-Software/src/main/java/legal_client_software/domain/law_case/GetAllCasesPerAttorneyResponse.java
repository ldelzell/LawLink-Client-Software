package legal_client_software.domain.law_case;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCasesPerAttorneyResponse {
    private List<Case> cases;
}
