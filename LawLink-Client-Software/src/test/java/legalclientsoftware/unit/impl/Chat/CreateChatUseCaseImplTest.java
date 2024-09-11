package legalclientsoftware.unit.impl.Chat;

import legal_client_software.business.chat.impl.CreateChatUseCaseImpl;
import legal_client_software.domain.chat.CreateChatMessageRequest;
import legal_client_software.domain.chat.CreateChatMessageResponse;
import legal_client_software.persistence.entity.MessageEntity;
import legal_client_software.persistence.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CreateChatUseCaseImplTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private CreateChatUseCaseImpl createChatUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
     void testSaveMessage() {

        CreateChatMessageRequest request = CreateChatMessageRequest.builder()
                .fromUserId(1L)
                .toUserId(2L)
                .text("Hello")
                .timestamp(LocalDateTime.now())
                .build();

        MessageEntity savedMessage = MessageEntity.builder()
                .id(1L)
                .fromUserId(request.getFromUserId())
                .toUserId(request.getToUserId())
                .text(request.getText())
                .timestamp(request.getTimestamp())
                .build();

        when(messageRepository.save(any(MessageEntity.class))).thenReturn(savedMessage);

        CreateChatMessageResponse response = createChatUseCase.saveMessage(request);

        assertEquals("1", String.valueOf(response.getMessageId()));
        verify(messageRepository, times(1)).save(any(MessageEntity.class));
    }
}
