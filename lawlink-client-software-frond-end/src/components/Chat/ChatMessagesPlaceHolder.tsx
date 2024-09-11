import React from 'react';
import { Message } from '../../types/Message';
import '../../styles/Components-CSS/Chat/ChatMessagesPH.css'

interface ChatMessagesPlaceholderProps {
  messagesReceived: Message[];
  user: { id: string; username: string } | null;
  attorney: { id: string; username: string } | null;
}

const ChatMessagesPlaceholder: React.FC<ChatMessagesPlaceholderProps> = ({ messagesReceived, user, attorney }) => {
  console.log('messagesReceived:', messagesReceived);
  return (
    <div className="chat-messages">
      {Array.isArray(messagesReceived) && messagesReceived.length > 0 ? (
        messagesReceived.map((message) => {
          const timestamp = new Date(message.timestamp);
          const formattedTimestamp = isNaN(timestamp.getTime()) ? 'Invalid Date' : timestamp.toLocaleString();
          return (
            <div
              key={`${message.id}-${message.timestamp}`}
              className={`message ${message.fromUserId === user?.id ? 'sent' : 'received'}`}
            >
              <div className="message-text">{message.text}</div>
              <div className="message-timestamp">{formattedTimestamp}</div>
            </div>
          );
        })
      ) : (
        <div>No messages</div>
      )}
    </div>
  );
};

export default ChatMessagesPlaceholder;
