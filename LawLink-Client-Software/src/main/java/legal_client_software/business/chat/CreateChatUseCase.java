package legal_client_software.business.chat;

import legal_client_software.domain.chat.CreateChatMessageRequest;
import legal_client_software.domain.chat.CreateChatMessageResponse;

public interface CreateChatUseCase {
    CreateChatMessageResponse saveMessage(CreateChatMessageRequest request);
}
