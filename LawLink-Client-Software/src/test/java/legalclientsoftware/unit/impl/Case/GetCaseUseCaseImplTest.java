package legalclientsoftware.unit.impl.Case;

import legal_client_software.business.law_case.impl.CaseConverter;
import legal_client_software.business.law_case.impl.GetCaseUseCaseImpl;
import legal_client_software.domain.law_case.Case;
import legal_client_software.persistence.entity.CaseEntity;
import legal_client_software.persistence.entity.ClientEntity;
import legal_client_software.persistence.repository.CaseRepository;
import legal_client_software.persistence.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

 class GetCaseUseCaseImplTest {

    @Mock
    private CaseRepository caseRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private GetCaseUseCaseImpl getCaseUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCaseById_CaseExists() {

        long caseId = 1L;
        CaseEntity caseEntity = new CaseEntity(caseId, "Type1", null, "Information1", true, false);
        Case expectedCase = CaseConverter.convert(caseEntity);

        when(caseRepository.findById(caseId)).thenReturn(Optional.of(caseEntity));

        Optional<Case> result = getCaseUseCase.getCaseById(caseId);

        assertTrue(result.isPresent());
        assertEquals(expectedCase, result.get());

        verify(caseRepository, times(1)).findById(caseId);
    }

    @Test
    void testGetCaseById_CaseNotExists() {

        long caseId = 1L;

        when(caseRepository.findById(caseId)).thenReturn(Optional.empty());

        Optional<Case> result = getCaseUseCase.getCaseById(caseId);

        assertTrue(result.isEmpty());

        verify(caseRepository, times(1)).findById(caseId);
    }
    @Test
    void testGetCaseByUserId_ClientNotExists() {

        long clientId = 1L;

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        Optional<Case> result = getCaseUseCase.getCaseByUserId(clientId);

        assertTrue(result.isEmpty());

        verify(clientRepository, times(1)).findById(clientId);
        verify(caseRepository, never()).findById(anyLong());
    }


    @Test
    void testGetCaseByUserId_CaseIdIsNull() {

        long clientId = 1L;
        ClientEntity clientEntity = new ClientEntity(clientId, null);

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(clientEntity));

        Optional<Case> result = getCaseUseCase.getCaseByUserId(clientId);

        assertTrue(result.isEmpty());

        verify(clientRepository, times(1)).findById(clientId);
        verify(caseRepository, never()).findById(anyLong());
    }
}