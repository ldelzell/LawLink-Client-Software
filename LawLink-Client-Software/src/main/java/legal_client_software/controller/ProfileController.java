package legal_client_software.controller;

import legal_client_software.business.GetClientUseCase;
import legal_client_software.domain.Client;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/profile")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ProfileController {
    private final GetClientUseCase getClientUseCase;
    @RolesAllowed({"CLIENT","ATTORNEY", "ADMIN"})
    @GetMapping("{id}")
    public ResponseEntity<Client> getClient(@PathVariable(value = "id") final long id){
        final Optional<Client> clientOptional = getClientUseCase.getClientByUserId(id);
        if(clientOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(clientOptional.get());
    }
}
