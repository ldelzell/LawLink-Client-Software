package legal_client_software.business.law_case.impl;

import legal_client_software.domain.law_case.Case;
import legal_client_software.persistence.entity.CaseEntity;

public class CaseConverter {
    private CaseConverter(){
    }
    public static Case convert(CaseEntity caseEntity){
        return Case.builder()
                .id(caseEntity.getId())
                .type(caseEntity.getType())
                .startingDate(caseEntity.getStartingDate())
                .information(caseEntity.getInformation())
                .status(caseEntity.getStatus())
                .isCaseWon(caseEntity.getIsCaseWon())
                .build();
    }
}
