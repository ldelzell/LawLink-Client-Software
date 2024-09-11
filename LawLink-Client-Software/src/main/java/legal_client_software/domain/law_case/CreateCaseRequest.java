package legal_client_software.domain.law_case;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCaseRequest {
    @NotNull
    private Long clientId;
    @NotNull
    private Long attorneyId;
    @NotNull
    private String type;
    @NotNull
    private Date startingDate;
    @NotBlank
    private String information;
    @NotNull
    private Boolean status;
    @NotNull
    private Boolean isCaseWon;
}
