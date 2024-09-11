package legal_client_software.domain.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateChatMessageResponse {
    private Long messageId;
}
