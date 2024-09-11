package legalclientsoftware.unit.impl.Case;

import legal_client_software.business.law_case.impl.GetAllCasesPerAttorneyImpl;
import legal_client_software.domain.law_case.GetAllCasesPerAttorneyResponse;
import legal_client_software.persistence.repository.AttorneyCaseRepository;
import legal_client_software.persistence.repository.CaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

 class GetAllCasesPerAttorneyUseCaseImplTest {
    @Mock
    private AttorneyCaseRepository attorneyCaseRepository;

    @Mock
    private CaseRepository caseRepository;

    @InjectMocks
    private GetAllCasesPerAttorneyImpl getAllCasesPerAttorney;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testGetAllCasesByAttorneyId_CasesFound() {

        List<Case> expectedCases = Arrays.asList(
                new Case(1, "Type1", "Sun Jun 09 17:37:39 EEST 2024", "Info1", true, true),
                new Case(2, "Type2", "Sun Jun 09 17:37:39 EEST 2024", "Info2", true, false),
                new Case(3, "Type3", "Sun Jun 09 17:37:39 EEST 2024", "Info3", true, true)
        );

        List<Case> actualCases = getAllCasesByAttorneyId();

        assertEquals(expectedCases.size(), actualCases.size(), "Size of lists does not match");

        for (int i = 0; i < expectedCases.size(); i++) {
            assertEquals(expectedCases.get(i), actualCases.get(i), "Case at index " + i + " does not match");
        }
    }

    private List<Case> getAllCasesByAttorneyId() {
        return Arrays.asList(
                new Case(1, "Type1", "Sun Jun 09 17:37:39 EEST 2024", "Info1", true, true),
                new Case(2, "Type2", "Sun Jun 09 17:37:39 EEST 2024", "Info2", true, false),
                new Case(3, "Type3", "Sun Jun 09 17:37:39 EEST 2024", "Info3", true, true)
        );
    }

    private static class Case {
        private int id;
        private String type;
        private String startingDate;
        private String information;
        private boolean status;
        private boolean isCaseWon;

        public Case(int id, String type, String startingDate, String information, boolean status, boolean isCaseWon) {
            this.id = id;
            this.type = type;
            this.startingDate = startingDate;
            this.information = information;
            this.status = status;
            this.isCaseWon = isCaseWon;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Case aCase = (Case) o;
            return id == aCase.id &&
                    status == aCase.status &&
                    isCaseWon == aCase.isCaseWon &&
                    type.equals(aCase.type) &&
                    startingDate.equals(aCase.startingDate) &&
                    information.equals(aCase.information);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, type, startingDate, information, status, isCaseWon);
        }
    }
    @Test
     void testGetAllCasesByAttorneyId_NoCasesFound() {

        Long attorneyId = 1L;
        when(attorneyCaseRepository.findAllByAttorneyId(attorneyId)).thenReturn(Collections.emptyList());

        GetAllCasesPerAttorneyResponse response = getAllCasesPerAttorney.getAllCasesByAttorneyId(attorneyId);

        assertEquals(Collections.emptyList(), response.getCases());
    }
}
