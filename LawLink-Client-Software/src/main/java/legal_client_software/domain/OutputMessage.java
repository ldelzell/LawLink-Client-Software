package legal_client_software.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
public class OutputMessage {
    private String from;
    private String text;
    private Date time;
}
