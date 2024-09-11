package legalclientsoftware.integration;
import com.fasterxml.jackson.databind.ObjectMapper;
import legal_client_software.LegalClientSoftwareApplication;
import legal_client_software.business.law_case.impl.CreateCaseUseCaseImpl;
import legal_client_software.business.law_case.impl.GetCaseUseCaseImpl;
import legal_client_software.business.law_case.impl.UpdateCaseUseCaseImpl;
import legal_client_software.configuration.security.token.AccessTokenEncoder;
import legal_client_software.configuration.security.token.impl.AccessTokenImpl;
import legal_client_software.domain.law_case.Case;
import legal_client_software.domain.law_case.CreateCaseRequest;
import legal_client_software.domain.law_case.CreateCaseResponse;
import legal_client_software.domain.law_case.UpdateCaseRequest;
import legal_client_software.persistence.entity.Role;
import legal_client_software.persistence.entity.UserEntity;
import legal_client_software.persistence.entity.UserRoleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = LegalClientSoftwareApplication.class)
@AutoConfigureMockMvc
class CaseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetCaseUseCaseImpl getCaseUseCase;
    @MockBean
    private UpdateCaseUseCaseImpl updateCaseUseCase;
    @MockBean
    private CreateCaseUseCaseImpl createCaseUseCase;

    @Autowired
    private AccessTokenEncoder accessTokenEncoder;
    private String jwtToken;

    @BeforeEach
    public void setUp() {
        UserEntity mockUser = new UserEntity();
        mockUser.setId(1L);
        mockUser.setUsername("testUser");

        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setRole(Role.ATTORNEY);
        mockUser.setUserRoles(Set.of(userRole));

        jwtToken = "Bearer " + generateAccessToken(mockUser);
    }

    private String generateAccessToken(UserEntity user) {
        Long userId = user.getId();
        Set<String> userRoles = Optional.ofNullable(user.getUserRoles())
                .orElse(Collections.emptySet())
                .stream()
                .map(userRole -> userRole.getRole().toString())
                .collect(Collectors.toSet());

        return accessTokenEncoder.encode(new AccessTokenImpl(user.getUsername(), userId, userRoles));
    }

    @Test
     void getCaseById_WithValidId_ReturnsCase() throws Exception {

        long caseId = 1L;
        Case mockedCase = new Case();
        mockedCase.setId(caseId);
        given(getCaseUseCase.getCaseById(caseId)).willReturn(Optional.of(mockedCase));

        mockMvc.perform(get("/cases/{id}", caseId)
                        .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(caseId));

        verify(getCaseUseCase).getCaseById(caseId);
    }

    @Test
     void updateCase_WithValidIdAndRequest_ReturnsNoContent() throws Exception {

        long caseId = 1L;
        UpdateCaseRequest request = new UpdateCaseRequest();
        request.setId(caseId);
        mockMvc.perform(put("/cases/{id}", caseId)
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"fieldName\":\"value\"}"))
                .andExpect(status().isNoContent());

        verify(updateCaseUseCase).updateCase(request);
    }
    @Test
     void createCase_WithValidRequest_ReturnsCreated() throws Exception {

        CreateCaseResponse mockedResponse = CreateCaseResponse.builder()
                .caseId(1L)
                .build();
        when(createCaseUseCase.createCase(any(CreateCaseRequest.class))).thenReturn(mockedResponse);

        CreateCaseRequest requestBody = CreateCaseRequest.builder()
                .clientId(1L)
                .attorneyId(1L)
                .type("Type")
                .startingDate(Date.from(Instant.parse("2022-06-24T00:00:00Z")))
                .information("Information")
                .status(true)
                .isCaseWon(true)
                .build();
        String requestBodyJson = new ObjectMapper().writeValueAsString(requestBody);

        mockMvc.perform(post("/cases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyJson)
                        .header("Authorization", jwtToken))
                .andExpect(status().isCreated());

        verify(createCaseUseCase).createCase(any(CreateCaseRequest.class));
    }
}
