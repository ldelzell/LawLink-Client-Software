package legal_client_software.business.impl;

import legal_client_software.domain.Client;
import legal_client_software.persistence.entity.ClientEntity;

public class ClientConverter {
    private ClientConverter(){
    }
    public static Client convert(ClientEntity client){
        return Client.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .password(client.getPassword())
                .caseId(client.getCaseId())
                .attorney(client.getAttorney())
                .build();
    }
}
