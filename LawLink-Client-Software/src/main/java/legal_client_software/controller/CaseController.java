package legal_client_software.controller;

import legal_client_software.business.law_case.CreateCaseUseCase;
import legal_client_software.business.law_case.GetCaseUseCase;
import legal_client_software.business.law_case.UpdateCaseUseCase;
import legal_client_software.domain.law_case.*;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cases")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CaseController {
    private final CreateCaseUseCase createCaseUseCase;
    private final GetCaseUseCase getCaseUseCase;
    private final UpdateCaseUseCase updateCaseUseCase;


    @RolesAllowed({"ATTORNEY"})
    @PostMapping()
    public ResponseEntity<CreateCaseResponse> createCase(@RequestBody @Valid CreateCaseRequest request) {
        CreateCaseResponse response = createCaseUseCase.createCase(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("{id}")
    public ResponseEntity<Case> getCase(@PathVariable(value = "id") final long id) {
        final Optional<Case> caseOptional = getCaseUseCase.getCaseById(id);
        if (caseOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(caseOptional.get());
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<Case> getCaseByUserId(@PathVariable(value = "id") final long id) {
        final Optional<Case> caseOptional = getCaseUseCase.getCaseByUserId(id);
        if (caseOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(caseOptional.get());
    }
    @RolesAllowed({"ATTORNEY"})
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCase(@PathVariable Long id, @RequestBody UpdateCaseRequest request) {
        request.setId(id);
        updateCaseUseCase.updateCase(request);
        return ResponseEntity.noContent().build();
    }
}
