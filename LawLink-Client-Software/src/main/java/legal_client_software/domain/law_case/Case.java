package legal_client_software.domain.law_case;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Case {
    private Long id;
    private String type;
    private Date startingDate;
    private String information;
    private Boolean status;
    private Boolean isCaseWon;
}
