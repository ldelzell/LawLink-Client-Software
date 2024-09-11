package legalclientsoftware.unit.Controller;

import legal_client_software.business.CreateClientUseCase;
import legal_client_software.business.attorney.*;
import legal_client_software.business.law_case.GetAllCasesPerAttorney;
import legal_client_software.business.law_case.impl.GetAllCasesPerAttorneyImpl;
import legal_client_software.controller.AttorneyController;
import legal_client_software.domain.attorney.*;
import jakarta.validation.Valid;
import legal_client_software.domain.law_case.GetAllCasesPerAttorneyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class AttorneyControllerTest {

    private CreateClientUseCase createClientUseCase;
    private GetAttorneyUseCase getAttorneyUseCase;
    private GetAttorneyByClient getAttorneyByClient;
    private GetClientsByAttorneyIdUseCase getClientsByAttorneyIdUseCase;
    private GetAllCasesPerAttorney getAllCasesPerAttorney;
    private UpdateAttorneyUseCase updateAttorneyUseCase;
    private GetWinRateUseCase getWinRateUseCase;
    private AttorneyController attorneyController;

    @BeforeEach
    void setUp() {
        createClientUseCase = mock(CreateClientUseCase.class);
        getAttorneyUseCase = mock(GetAttorneyUseCase.class);
        getAttorneyByClient = mock(GetAttorneyByClient.class);
        getClientsByAttorneyIdUseCase = mock(GetClientsByAttorneyIdUseCase.class);
        getAllCasesPerAttorney = mock(GetAllCasesPerAttorneyImpl.class);
        updateAttorneyUseCase = mock(UpdateAttorneyUseCase.class);
        getWinRateUseCase = mock(GetWinRateUseCase.class);
        attorneyController = new AttorneyController(createClientUseCase, getAttorneyUseCase, getClientsByAttorneyIdUseCase, getAllCasesPerAttorney,   updateAttorneyUseCase, getWinRateUseCase,getAttorneyByClient);
    }

    @Test
    void createAttorney_ValidRequest_ReturnsCreated() {
        CreateAttorneyRequest request = new CreateAttorneyRequest();
        CreateAttorneyResponse response = new CreateAttorneyResponse(1L);
        when(createClientUseCase.createAttorney(request)).thenReturn(response);

        ResponseEntity<CreateAttorneyResponse> result = attorneyController.createAttorney(request);

        assertSame(response, result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void getAttorney_ExistingId_ReturnsAttorney() {
        long id = 1L;
        Optional<Attorney> optionalAttorney = Optional.of(new Attorney());
        when(getAttorneyUseCase.getAttorneyByUserId(id)).thenReturn(optionalAttorney);

        ResponseEntity<Attorney> result = attorneyController.getAttorney(id);

        assertSame(optionalAttorney.get(), result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getAttorney_NonExistingId_ReturnsNotFound() {
        long id = 1L;
        Optional<Attorney> optionalAttorney = Optional.empty();
        when(getAttorneyUseCase.getAttorneyByUserId(id)).thenReturn(optionalAttorney);

        ResponseEntity<Attorney> result = attorneyController.getAttorney(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void getAttorneyByClient_ExistingClientId_ReturnsAttorney() {
        long clientId = 1L;
        Optional<Attorney> optionalAttorney = Optional.of(new Attorney());
        when(getAttorneyByClient.getAttorneyByClient(clientId)).thenReturn(optionalAttorney);

        ResponseEntity<Attorney> result = attorneyController.getAttorneyByClient(clientId);

        assertSame(optionalAttorney.get(), result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getAttorneyByClient_NonExistingClientId_ReturnsNotFound() {
        long clientId = 1L;
        Optional<Attorney> optionalAttorney = Optional.empty();
        when(getAttorneyByClient.getAttorneyByClient(clientId)).thenReturn(optionalAttorney);

        ResponseEntity<Attorney> result = attorneyController.getAttorneyByClient(clientId);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void getClientsByAttorney_ExistingAttorneyId_ReturnsClients() {
        long attorneyId = 1L;
        GetClientsByAttorneyResponse response = new GetClientsByAttorneyResponse();
        when(getClientsByAttorneyIdUseCase.getClientsByAttorneyId(attorneyId)).thenReturn(response);

        ResponseEntity<GetClientsByAttorneyResponse> result = attorneyController.getClientsByAttorney(attorneyId);

        assertSame(response, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getAllCasesByAttorneyId_ExistingAttorneyId_ReturnsCases() {
        long attorneyId = 1L;
        GetAllCasesPerAttorneyResponse response = new GetAllCasesPerAttorneyResponse();
        when(getAllCasesPerAttorney.getAllCasesByAttorneyId(attorneyId)).thenReturn(response);

        ResponseEntity<GetAllCasesPerAttorneyResponse> result = attorneyController.getAllCasesByAttorneyId(attorneyId);

        assertSame(response, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void updateAttorney_ValidRequest_ReturnsNoContent() {
        long id = 1L;
        UpdateAttorneyRequest request = new UpdateAttorneyRequest();
        request.setId(id);

        ResponseEntity<Void> result = attorneyController.updateAttorney(id, request);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(updateAttorneyUseCase).updateAttorney(request);
    }

    @Test
    void getWinRateForAttorney_ExistingAttorneyId_ReturnsWinRate() {
        long attorneyId = 1L;
        double winRate = 0.75;
        when(getWinRateUseCase.getWinRateForAttorney(attorneyId)).thenReturn(winRate);

        ResponseEntity<Double> result = attorneyController.getWinRateForAttorney(attorneyId);

        assertEquals(winRate, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getAttorneyWinRates_ReturnsAttorneyStats() {
        List<AttorneyStats> attorneyStatsList = List.of(new AttorneyStats());
        when(getWinRateUseCase.getAttorneysStats()).thenReturn(attorneyStatsList);

        List<AttorneyStats> result = attorneyController.getAttorneyWinRates();

        assertSame(attorneyStatsList, result);
    }
}
