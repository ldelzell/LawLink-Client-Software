import React, { useState, useEffect, useRef } from 'react';
import { Client, IMessage } from '@stomp/stompjs';
import { v4 as uuidv4 } from 'uuid';
import SendMessagePlaceholder from '../components/Chat/SendMessagePlaceHolder';
import ChatMessagesPlaceholder from '../components/Chat/ChatMessagesPlaceHolder';
import accountApi from '../api/accountApi';
import attorneyApi from '../api/attorneyApi';
import ListOfClients from '../components/Client/ListOfClients';
import '../styles/Pages-CSS/Chat.css';
import axios from 'axios';
import { Account } from '../types/Account';
import NavBar from '../components/NavBar';

interface Message {
  id: string;
  fromUserId: string;
  toUserId: string;
  text: string;
  timestamp: string;
}

interface User {
  id: string;
  username: string;
  role: string;
  firstName: string;
}

interface Attorney {
  id: string;
  firstName: string;
  username: string;
  role: string; 
}

function Chat() {
  const [stompClient, setStompClient] = useState<Client | null>(null);
  const [user, setUser] = useState<User | null>(null);
  const [selectedChatUser, setSelectedChatUser] = useState<User | Attorney | Account | null>(null);
  const [messagesReceived, setMessagesReceived] = useState<Message[]>([]);
  const [accounts, setAccounts] = useState<Account[]>([]);
  const [attorney, setAttorney] = useState<Attorney | null>(null);
  const [attorneyByClient, setAttorneyByClient] = useState<Attorney | null>(null);

  const stompClientInitialized = useRef(false);

  useEffect(() => {
    const accessToken = localStorage.getItem('accessToken');
    const userId = localStorage.getItem('id');
    const role = localStorage.getItem('userRoles');
    const attorneyId = localStorage.getItem('attorneyId') as string;

    if (userId && accessToken) {
      const config = {
        headers: {
          Authorization: `Bearer ${accessToken}`
        }
      };

      const fetchUserDetails = async () => {
        try {
          let userData;
          if (role === "ATTORNEY") {
            userData = await attorneyApi.getAccount(userId, config);
            setAttorney(userData);
            await fetchAccountsByAttorney(attorneyId, config); 
          } else {
            userData = await accountApi.getProfile(userId, config);
            setUser(userData);
          }

          if (userData.firstName) {
            setUser({ ...userData, role: role });
            if (role === "ATTORNEY") {
              setSelectedChatUser(userData);
              setupStompClient(userData.firstName);
            } else {
              setAttorney(userData);
              setSelectedChatUser(userData);
              setupStompClient(userData.firstName);
            }
          } else {
            console.error('Error: firstName is undefined in userData', userData);
          }
        } catch (error) {
          console.error('Error fetching user details:', error);
        }
      };

      fetchUserDetails();
    }

    const fetchAccountsByAttorney = async (attorneyId: string, config: any) => {
      try {
        const response = await attorneyApi.getClientsByAttorney(attorneyId, config);
        if (Array.isArray(response.clients)) {
          setAccounts(response.clients);
        } else {
          console.error('Invalid accounts data:', response);
        }
      } catch (error) {
        console.error('Error fetching accounts by attorney:', error);
      }
    };
  }, []);

  const fetchMessages = async (fromUser: string, toUser: string) => {
    try {
      const response = await axios.get<Message[]>(`/messages?fromUser=${fromUser}&toUser=${toUser}`);
      if (Array.isArray(response.data)) {
        setMessagesReceived(response.data);
      } else {
        setMessagesReceived([]); 
      }
    } catch (error) {
      console.error('Error fetching messages:', error);
      setMessagesReceived([]);
    }
  };
  

  const setupStompClient = (firstName: string) => {
    if (stompClientInitialized.current) return;
    stompClientInitialized.current = true;
  
    const stompClient = new Client({
      brokerURL: 'ws://localhost:8080/ws',
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000
    });
  
    stompClient.onConnect = () => {
      stompClient.subscribe('/topic/publicmessages', (data) => {
        onMessageReceived(data);
      });
  
      stompClient.subscribe(`/user/${firstName}/queue/inboxmessages`, (data) => {
        onMessageReceived(data);
      });
    };
  
    stompClient.activate();
    setStompClient(stompClient);
  };
  

  const sendMessage = async (text: string) => {
    if (user && selectedChatUser) {
      const timestamp = new Date().toISOString(); 
      const payload: Message = {
        id: uuidv4(),
        fromUserId: user.id,
        toUserId: selectedChatUser.id,
        text: text,
        timestamp: timestamp, 
      };
  
      console.log('Sending message payload:', payload); 
  
      try {
        const accessToken = localStorage.getItem('accessToken');
        if (!accessToken) {
          throw new Error('Access token not found');
        }
        stompClient?.publish({destination: "/topic/publicmessages", body: JSON.stringify(payload)});
        const response = await axios.post('http://localhost:8080/messages', payload, {
          headers: {
            Authorization: `Bearer ${accessToken}`
          }
        });
  
        console.log('Message send response:', response.data);
  
        //shawol copy
        setMessagesReceived(messagesReceived => [...messagesReceived, payload]);
      } catch (error) {
        console.error('Error sending message:', error);
      }
    }
  };
  
  
  const onMessageReceived = (data: IMessage) => {
    
    const message: Message = JSON.parse(data.body);
    setMessagesReceived(messagesReceived => {
      if (!messagesReceived.find(msg => msg.id === message.id)) {
        return [...messagesReceived, { ...message, id: uuidv4() }];
      }
      return messagesReceived;
    });
  };

  const handleChatSelection = async (chatUser: User | Account) => {
    setSelectedChatUser(chatUser);
    if (user && chatUser) {
      try {
        const accessToken = localStorage.getItem('accessToken');
        if (!accessToken) {
          throw new Error('Access token not found');
        }
  
        const response = await axios.get<Message[]>(`http://localhost:8080/messages?fromUserId=${user.id}&toUserId=${chatUser.id}`, {
          headers: {
            Authorization: `Bearer ${accessToken}`
          }
        });
        console.log(response);
        
  
        if (Array.isArray(response.data)) {
          setMessagesReceived(response.data);
        } else {
          setMessagesReceived([]);
        }
      } catch (error) {
        console.error('Error fetching messages:', error);
        setMessagesReceived([]);
      }
    }
  };
  

  return (
    <div className="main-container">
      <NavBar/>
      <div className="sidebar">
        {user?.role === 'ATTORNEY' ? (
          accounts.length > 0 ? (
            <ListOfClients accounts={accounts} onChatSelection={handleChatSelection} />
          ) : (
            <div>Loading accounts...</div>
          )
        ) : (
          attorney ? (
            <div className="chat-list-item" onClick={() => handleChatSelection(attorney)}>
              {attorney.firstName}
            </div>
          ) : (
            <div>Loading attorney data...</div>
          )
        )}
      </div>
      <div className="chat-window">
        {selectedChatUser ? (
          <>
            <div className="chat-header">
              <div>You are chatting with: {selectedChatUser.firstName}</div>
            </div>
            <ChatMessagesPlaceholder messagesReceived={messagesReceived} user={user} attorney={selectedChatUser} />
            <SendMessagePlaceholder onMessageSend={sendMessage} />
          </>
        ) : (
          <div className="no-chat-selected">Select a chat to start messaging</div>
        )}
      </div>
    </div>
  );
}

export default Chat;
