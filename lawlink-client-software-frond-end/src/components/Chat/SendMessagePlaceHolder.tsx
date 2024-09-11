import React, { useState } from 'react';
import '../../styles/Components-CSS/Chat/SendMessagePH.css'

interface SendMessagePlaceholderProps {
  onMessageSend: (text: string) => void;
}

const SendMessagePlaceholder: React.FC<SendMessagePlaceholderProps> = ({ onMessageSend }) => {
  const [message, setMessage] = useState('');

  const handleSend = () => {
    if (message.trim() !== '') {
      onMessageSend(message);
      setMessage('');
    }
  };

  return (
    <div className="send-message">
      <input
        type="text"
        value={message}
        onChange={(e) => setMessage(e.target.value)}
        placeholder="Type a message..."
      />
      <button onClick={handleSend}>Send</button>
    </div>
  );
};

export default SendMessagePlaceholder;
