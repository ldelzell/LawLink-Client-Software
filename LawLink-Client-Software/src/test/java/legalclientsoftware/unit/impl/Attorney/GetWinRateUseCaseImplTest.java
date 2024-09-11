package legalclientsoftware.unit.impl.Attorney;

import legal_client_software.business.attorney.impl.GetWinRateUseCaseImpl;
import legal_client_software.domain.attorney.AttorneyStats;
import legal_client_software.persistence.repository.AttorneyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

 class GetWinRateUseCaseImplTest {

    @Mock
    private AttorneyRepository attorneyRepository;

    @InjectMocks
    private GetWinRateUseCaseImpl getWinRateUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testGetWinRateForAttorney_Success() {

        Long attorneyId = 1L;
        Double expectedWinRate = 75.0;
        when(attorneyRepository.getWinRateForAttorney(attorneyId)).thenReturn(expectedWinRate);

        Double result = getWinRateUseCase.getWinRateForAttorney(attorneyId);

        assertEquals(expectedWinRate, result);
    }

    @Test
     void testGetWinRateForAttorney_NoData() {

        Long attorneyId = 1L;
        when(attorneyRepository.getWinRateForAttorney(attorneyId)).thenReturn(null);

        Double result = getWinRateUseCase.getWinRateForAttorney(attorneyId);

        assertEquals(null, result);
    }
    @Test
    void getAttorneysStats_ReturnsAttorneyStats() {
       List<Object[]> results = Arrays.asList(
               new Object[]{1L, "John Doe", "Law Firm", 10L},
               new Object[]{2L, "Jane Smith", "Legal Services", 15L}
       );
       when(attorneyRepository.getAttorneysStats()).thenReturn(results);

       List<AttorneyStats> expectedStats = Arrays.asList(
               new AttorneyStats(1L, "John Doe", "Law Firm", 10L),
               new AttorneyStats(2L, "Jane Smith", "Legal Services", 15L)
       );

       List<AttorneyStats> actualStats = getWinRateUseCase.getAttorneysStats();

       assertEquals(expectedStats.size(), actualStats.size());
       for (int i = 0; i < expectedStats.size(); i++) {
          assertEquals(expectedStats.get(i), actualStats.get(i));
       }
    }
}
