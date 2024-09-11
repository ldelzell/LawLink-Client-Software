package legalclientsoftware.unit.Controller;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import legal_client_software.business.law_case.CreateCaseUseCase;
import legal_client_software.business.law_case.GetCaseUseCase;
import legal_client_software.business.law_case.UpdateCaseUseCase;
import legal_client_software.controller.CaseController;
import legal_client_software.domain.law_case.Case;
import legal_client_software.domain.law_case.CreateCaseRequest;
import legal_client_software.domain.law_case.CreateCaseResponse;
import legal_client_software.domain.law_case.UpdateCaseRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class CaseControllerTest {

    private CreateCaseUseCase createCaseUseCase;
    private GetCaseUseCase getCaseUseCase;
    private UpdateCaseUseCase updateCaseUseCase;
    private CaseController caseController;

    @BeforeEach
    void setUp() {
        createCaseUseCase = mock(CreateCaseUseCase.class);
        getCaseUseCase = mock(GetCaseUseCase.class);
        updateCaseUseCase = mock(UpdateCaseUseCase.class);
        caseController = new CaseController(createCaseUseCase, getCaseUseCase, updateCaseUseCase);
    }

    @Test
    void createCase_ValidRequest_ReturnsCreated() {

        CreateCaseRequest request = new CreateCaseRequest();
        CreateCaseResponse response = new CreateCaseResponse(1L);
        when(createCaseUseCase.createCase(request)).thenReturn(response);


        ResponseEntity<CreateCaseResponse> result = caseController.createCase(request);


        assertSame(response, result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void getCase_ExistingId_ReturnsCase() {

        long id = 1L;
        Optional<Case> optionalCase = Optional.of(new Case());
        when(getCaseUseCase.getCaseById(id)).thenReturn(optionalCase);

        ResponseEntity<Case> result = caseController.getCase(id);

        assertSame(optionalCase.get(), result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getCase_NonExistingId_ReturnsNotFound() {

        long id = 1L;
        Optional<Case> optionalCase = Optional.empty();
        when(getCaseUseCase.getCaseById(id)).thenReturn(optionalCase);

        ResponseEntity<Case> result = caseController.getCase(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void getCaseByUserId_ExistingUserId_ReturnsCase() {

        long userId = 1L;
        Optional<Case> optionalCase = Optional.of(new Case());
        when(getCaseUseCase.getCaseByUserId(userId)).thenReturn(optionalCase);

        ResponseEntity<Case> result = caseController.getCaseByUserId(userId);

        assertSame(optionalCase.get(), result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getCaseByUserId_NonExistingUserId_ReturnsNotFound() {

        long userId = 1L;
        Optional<Case> optionalCase = Optional.empty();
        when(getCaseUseCase.getCaseByUserId(userId)).thenReturn(optionalCase);

        ResponseEntity<Case> result = caseController.getCaseByUserId(userId);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void updateCase_ValidRequest_ReturnsNoContent() {

        long id = 1L;
        UpdateCaseRequest request = new UpdateCaseRequest();
        request.setId(id);

        ResponseEntity<Void> result = caseController.updateCase(id, request);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(updateCaseUseCase).updateCase(request);
    }
}
