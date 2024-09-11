package legalclientsoftware.unit.Controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import legal_client_software.business.chat.CreateChatUseCase;
import legal_client_software.business.chat.GetChatUseCase;
import legal_client_software.controller.ChatController;
import legal_client_software.domain.chat.CreateChatMessageRequest;
import legal_client_software.domain.chat.CreateChatMessageResponse;
import legal_client_software.persistence.entity.MessageEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;


class ChatControllerTest {

    private SimpMessagingTemplate messagingTemplate;
    private CreateChatUseCase createChatUseCase;
    private GetChatUseCase getChatUseCase;
    private ChatController chatController;

    @BeforeEach
    void setUp() {
        messagingTemplate = mock(SimpMessagingTemplate.class);
        createChatUseCase = mock(CreateChatUseCase.class);
        getChatUseCase = mock(GetChatUseCase.class);
        chatController = new ChatController(messagingTemplate, createChatUseCase, getChatUseCase);
    }

    @Test
    void sendMessage_ValidRequest_ReturnsCreated() {

        CreateChatMessageRequest request = new CreateChatMessageRequest();
        CreateChatMessageResponse response = new CreateChatMessageResponse(1L);
        when(createChatUseCase.saveMessage(request)).thenReturn(response);

        ResponseEntity<CreateChatMessageResponse> result = chatController.sendMessage(request);

        assertSame(response, result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void getMessages_ValidRequest_ReturnsMessages() {

        long fromUserId = 1L;
        long toUserId = 2L;
        List<MessageEntity> messages = new ArrayList<>();
        when(getChatUseCase.getMessages(fromUserId, toUserId)).thenReturn(messages);

        ResponseEntity<List<MessageEntity>> result = chatController.getMessages(fromUserId, toUserId);

        assertSame(messages, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
