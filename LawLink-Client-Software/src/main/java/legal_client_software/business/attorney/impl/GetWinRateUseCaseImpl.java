package legal_client_software.business.attorney.impl;

import legal_client_software.business.attorney.GetWinRateUseCase;
import legal_client_software.domain.attorney.AttorneyStats;
import legal_client_software.persistence.repository.AttorneyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class GetWinRateUseCaseImpl implements GetWinRateUseCase {
    private final AttorneyRepository attorneyRepository;

    @Override
    public Double getWinRateForAttorney(Long attorneyId) {
        return attorneyRepository.getWinRateForAttorney(attorneyId);
    }
    @Override
    public List<AttorneyStats> getAttorneysStats() {
        List<Object[]> results = attorneyRepository.getAttorneysStats();
        return results.stream()
                .map(result -> new AttorneyStats(
                        (Long) result[0],
                        (String) result[1],
                        (String) result[2],
                        (Long) result[3])
                )
                .collect(Collectors.toList());
    }

}
