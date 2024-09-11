package legal_client_software.controller;

import legal_client_software.business.*;
import legal_client_software.business.CreateClientUseCase;
import legal_client_software.business.DeleteClientUseCase;
import legal_client_software.business.GetClientUseCase;
import legal_client_software.domain.*;
import legal_client_software.domain.CreateClientRequest;
import legal_client_software.domain.CreateClientResponse;
import legal_client_software.domain.Client;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ClientController {
    private final CreateClientUseCase createClientUseCase;
    private final GetClientUseCase getClientUseCase;
    private final DeleteClientUseCase deleteClientUseCase;
    private final UpdateClientUseCase updateClientUseCase;
    private final GetClientsUseCase getClientsUseCase;

    @RolesAllowed({"ATTORNEY", "ADMIN"})
    @PostMapping()
    public ResponseEntity<CreateClientResponse> createClient(@RequestBody @Valid CreateClientRequest request) {
        CreateClientResponse response = createClientUseCase.createClient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @RolesAllowed({"CLIENT","ATTORNEY", "ADMIN"})
    @GetMapping("{id}")
    public ResponseEntity<Client> getClient2(@PathVariable(value = "id") final long id){
        final Optional<Client> clientOptional = getClientUseCase.getClient(id);
        if(clientOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(clientOptional.get());
    }
    @RolesAllowed({"ATTORNEY","ADMIN"})
    @GetMapping
    public ResponseEntity<GetClientsResponse> getClients(){
        GetClientsResponse response = getClientsUseCase.getClients();
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteClient(@PathVariable int userId){
        deleteClientUseCase.deleteClient(userId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("{id}")
    public ResponseEntity<Void> updateClient(@PathVariable("id") long id,
                                              @RequestBody @Validated UpdateClientRequest request) {
        request.setId(id);
        updateClientUseCase.updateClient(request);
        return ResponseEntity.ok().build();
    }

}
