package legal_client_software.business.attorney.impl;

import legal_client_software.domain.attorney.Attorney;
import legal_client_software.persistence.entity.AttorneyEntity;

public class AttorneyConverter {
    private AttorneyConverter(){
    }
    public static Attorney convert(AttorneyEntity attorney){
        return Attorney.builder()
                .id(attorney.getId())
                .firstName(attorney.getFirstName())
                .lastName(attorney.getLastName())
                .email(attorney.getEmail())
                .password(attorney.getPassword())
                .build();
    }
}
