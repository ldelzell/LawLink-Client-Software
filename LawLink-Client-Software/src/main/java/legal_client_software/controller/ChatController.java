package legal_client_software.controller;

import legal_client_software.business.chat.CreateChatUseCase;
import legal_client_software.business.chat.GetChatUseCase;
import legal_client_software.domain.chat.CreateChatMessageRequest;
import legal_client_software.domain.chat.CreateChatMessageResponse;
import legal_client_software.domain.ChatMessage;
import legal_client_software.persistence.entity.MessageEntity;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/messages")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final CreateChatUseCase createChatUseCase;
    private final GetChatUseCase getChatUseCase;
    @RolesAllowed({"ATTORNEY","CLIENT"})
    @PostMapping()
    public ResponseEntity<CreateChatMessageResponse> sendMessage(@RequestBody CreateChatMessageRequest request) {
        CreateChatMessageResponse response = createChatUseCase.saveMessage(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @RolesAllowed({"ATTORNEY","CLIENT"})
    @GetMapping
    public ResponseEntity<List<MessageEntity>> getMessages(
            @RequestParam Long fromUserId,
            @RequestParam Long toUserId) {
        List<MessageEntity> messages = getChatUseCase.getMessages(fromUserId, toUserId);
        return ResponseEntity.ok(messages);
    }
        @RolesAllowed({"ATTORNEY", "CLIENT"})
        @PostMapping("/ws")
        public ResponseEntity<Void> sendNotificationToUsers(@RequestBody ChatMessage message) {
            messagingTemplate.convertAndSend("/topic/publicmessages", message);
            return ResponseEntity.status(HttpStatus.CREATED).build();

        }
}