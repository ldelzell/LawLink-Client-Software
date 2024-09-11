package legal_client_software.business.chat;

import legal_client_software.persistence.entity.MessageEntity;

import java.util.List;

public interface GetChatUseCase {
    List<MessageEntity> getMessages(Long fromUserId, Long toUserId);
}
