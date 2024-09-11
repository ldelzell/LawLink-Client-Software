package legal_client_software.business.attorney;

import legal_client_software.domain.attorney.Attorney;

import java.util.Optional;

public interface GetAttorneyByClient {
    Optional<Attorney> getAttorneyByClient(long clientId);

}
