package legalclientsoftware.unit.impl.Chat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import legal_client_software.business.chat.impl.GetChatUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import legal_client_software.persistence.entity.MessageEntity;
import legal_client_software.persistence.repository.MessageRepository;

@ExtendWith(MockitoExtension.class)
class GetChatUseCaseImplTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private GetChatUseCaseImpl getChatUseCase;

    @Test
     void testGetMessages_Success() {

        Long fromUserId = 1L;
        Long toUserId = 2L;

        MessageEntity message1 = new MessageEntity(1L, 1L, 2L, "Hello", LocalDateTime.now());
        MessageEntity message2 = new MessageEntity(2L, 2L, 1L, "Hi", LocalDateTime.now());
        List<MessageEntity> mockMessages = Arrays.asList(message1, message2);

        when(messageRepository.findByToUserId(toUserId)).thenReturn(mockMessages);

        List<MessageEntity> actualMessages = getChatUseCase.getMessages(fromUserId, toUserId);

        assertEquals(2, actualMessages.size());
        assertEquals(message1.getId(), actualMessages.get(0).getId());
        assertEquals(message1.getFromUserId(), actualMessages.get(0).getFromUserId());
        assertEquals(message1.getToUserId(), actualMessages.get(0).getToUserId());
        assertEquals(message1.getText(), actualMessages.get(0).getText());
        assertEquals(message1.getTimestamp(), actualMessages.get(0).getTimestamp());

        assertEquals(message2.getId(), actualMessages.get(1).getId());
        assertEquals(message2.getFromUserId(), actualMessages.get(1).getFromUserId());
        assertEquals(message2.getToUserId(), actualMessages.get(1).getToUserId());
        assertEquals(message2.getText(), actualMessages.get(1).getText());
        assertEquals(message2.getTimestamp(), actualMessages.get(1).getTimestamp());
    }
}
