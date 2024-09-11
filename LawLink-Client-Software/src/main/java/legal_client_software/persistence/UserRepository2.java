package legal_client_software.persistence;

import legal_client_software.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;


public interface UserRepository2 {

    UserEntity save(UserEntity user);
    boolean existsByEmail(String email);
    Optional<UserEntity> getById(long userId);
    void deleteById(long userId);
    List<UserEntity> findAll();

}
