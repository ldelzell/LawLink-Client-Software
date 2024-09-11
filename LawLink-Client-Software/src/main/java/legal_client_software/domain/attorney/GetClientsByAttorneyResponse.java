package legal_client_software.domain.attorney;

import legal_client_software.domain.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
public class GetClientsByAttorneyResponse {
    private List<Client> clients;
}
