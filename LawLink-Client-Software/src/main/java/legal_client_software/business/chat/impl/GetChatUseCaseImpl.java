package legal_client_software.business.chat.impl;

import legal_client_software.business.chat.GetChatUseCase;
import legal_client_software.persistence.entity.MessageEntity;
import legal_client_software.persistence.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class    GetChatUseCaseImpl implements GetChatUseCase {
    private final MessageRepository messageRepository;

    @Override
    public List<MessageEntity> getMessages(Long fromUserId, Long toUserId) {
        return messageRepository.findByToUserId(toUserId);
    }
}

