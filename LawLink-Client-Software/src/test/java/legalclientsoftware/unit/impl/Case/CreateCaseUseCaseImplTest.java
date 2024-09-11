package legalclientsoftware.unit.impl.Case;

import legal_client_software.business.impl.EmailUseCaseImpl;
import legal_client_software.business.law_case.CreateCaseUseCase;
import legal_client_software.business.law_case.impl.CreateCaseUseCaseImpl;
import legal_client_software.domain.law_case.CreateCaseRequest;
import legal_client_software.domain.law_case.CreateCaseResponse;
import legal_client_software.persistence.entity.CaseEntity;
import legal_client_software.persistence.entity.ClientEntity;
import legal_client_software.persistence.repository.AttorneyCaseRepository;
import legal_client_software.persistence.repository.CaseRepository;
import legal_client_software.persistence.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class CreateCaseUseCaseImplTest {

    @Mock
    private CaseRepository caseRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AttorneyCaseRepository attorneyCaseRepository;
     @Mock
     private EmailUseCaseImpl emailUseCase;


    @InjectMocks
    private CreateCaseUseCase useCase = new CreateCaseUseCaseImpl(caseRepository, clientRepository, attorneyCaseRepository,emailUseCase);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


}

