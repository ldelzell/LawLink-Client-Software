package legal_client_software.business.attorney.impl;

import legal_client_software.business.attorney.GetAttorneyUseCase;
import legal_client_software.domain.attorney.Attorney;
import legal_client_software.persistence.entity.AttorneyEntity;
import legal_client_software.persistence.entity.UserEntity;
import legal_client_software.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class GetAttorneyUseCaseImpl implements GetAttorneyUseCase {
    private final UserRepository userRepository;
    @Override
    public Optional<Attorney> getAttorneyByUserId(long userId){
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            AttorneyEntity attorney = user.getAttorney();
            if(attorney != null) {
                return Optional.of(AttorneyConverter.convert(attorney));
            }
        }
        return Optional.empty();
    }
}
