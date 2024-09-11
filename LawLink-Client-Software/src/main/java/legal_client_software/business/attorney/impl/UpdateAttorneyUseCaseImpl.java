package legal_client_software.business.attorney.impl;

import legal_client_software.business.attorney.UpdateAttorneyUseCase;
import legal_client_software.business.exception.InvalidUserException;
import legal_client_software.domain.attorney.UpdateAttorneyRequest;
import legal_client_software.persistence.entity.AttorneyEntity;
import legal_client_software.persistence.repository.AttorneyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateAttorneyUseCaseImpl implements UpdateAttorneyUseCase {
    private final AttorneyRepository attorneyRepository;

    @Override
    public void updateAttorney(UpdateAttorneyRequest request) {
        Optional<AttorneyEntity> attorneyOptional = attorneyRepository.findById(request.getId());
        if (attorneyOptional.isEmpty()) {
            throw new InvalidUserException("INVALID_USER");
        }

        AttorneyEntity attorney = attorneyOptional.get();
        updateFields(request, attorney);
    }

    private void updateFields(UpdateAttorneyRequest request, AttorneyEntity attorney) {
        attorney.setFirstName(request.getFirstName());
        attorney.setLastName(request.getLastName());
        attorney.setEmail(request.getEmail());
        attorney.setPassword(request.getPassword());
        attorneyRepository.save(attorney);
    }
}
