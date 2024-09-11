package legal_client_software.controller;

import legal_client_software.business.law_case.GetAllCasesPerAttorney;
import legal_client_software.business.CreateClientUseCase;
import legal_client_software.business.attorney.*;
import legal_client_software.domain.law_case.GetAllCasesPerAttorneyResponse;
import legal_client_software.domain.attorney.*;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/attorneys")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AttorneyController {
    private final CreateClientUseCase createClientUseCase;
    private final GetAttorneyUseCase getAttorneyUseCase;
    private final GetClientsByAttorneyIdUseCase getClientsByAttorneyIdUseCase;
    private final GetAllCasesPerAttorney getAllCasesPerAttorney;
    private final UpdateAttorneyUseCase updateAttorneyUseCase;
    private final GetWinRateUseCase getWinRateUseCase;
    private final GetAttorneyByClient getAttorneyByClient;
    @PostMapping()
    public ResponseEntity<CreateAttorneyResponse> createAttorney(@RequestBody @Valid CreateAttorneyRequest request) {
        CreateAttorneyResponse response = createClientUseCase.createAttorney(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @RolesAllowed({"ATTORNEY"})
    @GetMapping("{id}")
    public ResponseEntity<Attorney> getAttorney(@PathVariable(value = "id") final long id){
        final Optional<Attorney> attorneyOptional = getAttorneyUseCase.getAttorneyByUserId(id);
        if(attorneyOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(attorneyOptional.get());
    }
    @GetMapping("/client/{id}")
    public ResponseEntity<Attorney> getAttorneyByClient(@PathVariable(value = "id") final long id){
        final Optional<Attorney> attorneyOptional = getAttorneyByClient.getAttorneyByClient(id);
        if(attorneyOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(attorneyOptional.get());
    }
    @RolesAllowed({"ATTORNEY"})
    @GetMapping("/{attorneyId}/clients")
    public ResponseEntity<GetClientsByAttorneyResponse> getClientsByAttorney(@PathVariable Long attorneyId) {
        GetClientsByAttorneyResponse response = getClientsByAttorneyIdUseCase.getClientsByAttorneyId(attorneyId);
        return ResponseEntity.ok(response);
    }
    @RolesAllowed({"ATTORNEY"})
    @GetMapping("/{attorneyId}/cases")
    public ResponseEntity<GetAllCasesPerAttorneyResponse> getAllCasesByAttorneyId(@PathVariable Long attorneyId) {
        GetAllCasesPerAttorneyResponse response = getAllCasesPerAttorney.getAllCasesByAttorneyId(attorneyId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAttorney(@PathVariable Long id, @RequestBody UpdateAttorneyRequest request) {
        request.setId(id);
        updateAttorneyUseCase.updateAttorney(request);
        return ResponseEntity.noContent().build();
    }
    @RolesAllowed({"ATTORNEY"})
    @GetMapping("/{id}/winrate")
    public ResponseEntity<Double> getWinRateForAttorney(@PathVariable Long id) {
        Double winRate = getWinRateUseCase.getWinRateForAttorney(id);
        return ResponseEntity.ok(winRate);
    }
    @RolesAllowed({"ATTORNEY"})
    @GetMapping("/stats")
    public List<AttorneyStats> getAttorneyWinRates() {
        return getWinRateUseCase.getAttorneysStats();
    }
}
