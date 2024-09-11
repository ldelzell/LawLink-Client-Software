package legalclientsoftware.unit.impl.Case;

import legal_client_software.business.law_case.impl.UpdateCaseUseCaseImpl;
import legal_client_software.business.exception.InvalidCaseException;
import legal_client_software.domain.law_case.UpdateCaseRequest;
import legal_client_software.persistence.entity.CaseEntity;
import legal_client_software.persistence.repository.CaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class UpdateCaseUseCaseImplTest {

    @Mock
    private CaseRepository caseRepository;

    @InjectMocks
    private UpdateCaseUseCaseImpl updateCaseUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testUpdateCase_CaseNotFound() {

        UpdateCaseRequest request = new UpdateCaseRequest();
        request.setId(1L);

        when(caseRepository.findById(request.getId())).thenReturn(Optional.empty());

        InvalidCaseException exception = assertThrows(InvalidCaseException.class, () -> {
            updateCaseUseCase.updateCase(request);
        });

        assertEquals("INVALID_CASE", exception.getMessage());
    }

    @Test
     void testUpdateCase_CaseFound() {

        UpdateCaseRequest request = new UpdateCaseRequest();
        request.setId(1L);
        request.setType("Type");
        request.setInformation("Information");
        request.setStatus(true);
        request.setIsCaseWon(true);

        CaseEntity caseEntity = new CaseEntity();
        when(caseRepository.findById(request.getId())).thenReturn(Optional.of(caseEntity));

        updateCaseUseCase.updateCase(request);

        verify(caseRepository, times(1)).save(caseEntity);
        assertEquals("Type", caseEntity.getType());
        assertEquals("Information", caseEntity.getInformation());
        assertEquals(true, caseEntity.getStatus());
        assertEquals(true, caseEntity.getIsCaseWon());
    }

    @Test
     void testUpdateCase_InvalidCaseStatus() {

        UpdateCaseRequest request = new UpdateCaseRequest();
        request.setId(1L);
        request.setStatus(true);

        CaseEntity caseEntity = new CaseEntity();
        when(caseRepository.findById(request.getId())).thenReturn(Optional.of(caseEntity));

        InvalidCaseException exception = assertThrows(InvalidCaseException.class, () -> {
            updateCaseUseCase.updateCase(request);
        });

        assertEquals("Case won/lost status must be set if the case is finished.", exception.getMessage());
    }
}