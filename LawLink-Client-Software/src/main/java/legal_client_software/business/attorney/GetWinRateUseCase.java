package legal_client_software.business.attorney;

import legal_client_software.domain.attorney.AttorneyStats;

import java.util.List;

public interface GetWinRateUseCase {
    Double getWinRateForAttorney(Long attorneyId);
    List<AttorneyStats>getAttorneysStats();
}
