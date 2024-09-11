package legal_client_software.persistence.repository;

import legal_client_software.persistence.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findByFromUserIdAndToUserId(Long fromUserId, Long toUserId);
    List<MessageEntity> findByToUserId(Long toUserId);
}