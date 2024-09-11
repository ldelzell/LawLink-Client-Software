package legal_client_software.domain.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateChatMessageRequest {
    private Long fromUserId;
    private Long toUserId;
    private String text;
    private LocalDateTime timestamp;


}
