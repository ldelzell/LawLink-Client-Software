package legal_client_software.domain;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetClientsResponse {
    private List<Client> clients;
}

