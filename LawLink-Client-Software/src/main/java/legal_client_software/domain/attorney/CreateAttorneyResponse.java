package legal_client_software.domain.attorney;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateAttorneyResponse {
    private Long attorneyId;

}
