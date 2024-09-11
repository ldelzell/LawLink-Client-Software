package legal_client_software.business.chat.impl;

import legal_client_software.business.chat.CreateChatUseCase;
import legal_client_software.domain.chat.CreateChatMessageRequest;
import legal_client_software.domain.chat.CreateChatMessageResponse;
import legal_client_software.persistence.entity.MessageEntity;
import legal_client_software.persistence.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateChatUseCaseImpl implements CreateChatUseCase {
    private final MessageRepository messageRepository;

    @Override
    public CreateChatMessageResponse saveMessage(CreateChatMessageRequest request) {
        MessageEntity savedMessage = saveNewMessage(request);

                return CreateChatMessageResponse.builder()
                        .messageId(savedMessage.getId())
                        .build();
    }
    private MessageEntity saveNewMessage(CreateChatMessageRequest request) {
        MessageEntity newMessage = MessageEntity.builder()
                .text(request.getText())
                .fromUserId(request.getFromUserId())
                .toUserId(request.getToUserId())
                .timestamp(request.getTimestamp())
                .build();
        return messageRepository.save(newMessage);
    }

}
