package legal_client_software.persistence.impl;

import legal_client_software.persistence.entity.UserEntity;
import legal_client_software.persistence.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public abstract class UserRepositoryImpl implements UserRepository {
    private static long nextId = 1;
    private final List<UserEntity> savedUsers;

    protected UserRepositoryImpl() {
        this.savedUsers = new ArrayList<>();
    }

    @Override
    public UserEntity save(UserEntity user){
        if (user.getId() == null) {
            user.setId(nextId);
            nextId++;
            savedUsers.add(user);
        }
        return user;
    }

    @Override
    public List<UserEntity> findAll() {
        return Collections.unmodifiableList(savedUsers);
    }

}
