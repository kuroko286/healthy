import { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { selectUserData } from '../../redux/selectors';
import SockJS from 'sockjs-client';
import { over } from 'stompjs';
import axios from 'axios';

let client = null;
function ChatGptPage() {
  const {
    user: { email },
  } = useSelector(selectUserData);
  const [messages, setMessages] = useState([]);
  const [message, setMessage] = useState('');
  const [stompClient, setStompClient] = useState(null);
  useEffect(() => {
    const fetchMessages = async () => {
      try {
        const response = await axios.get('http://localhost:8080/v1/messages');
        setMessages(response.data);
      } catch (error) {
        console.error('Error fetching messages:', error.message);
      }
    };

    fetchMessages();
  }, []);

  useEffect(() => {
    const Sock = new SockJS('http://localhost:8080/chatgpt');
    client = over(Sock);

    client.connect(
      {},
      () => {
        client.subscribe('/user/' + email + '/private', (payload) => {
          // console.log('get messssage');
          const payloadData = JSON.parse(payload.body);
          setMessages((messages) => [...messages, payloadData]);
        });
      },
      onError
    );
    setStompClient(client);
    return () => {
      // console.log(client);
      if (client.connected) {
        client.disconnect();
      }
    };
  }, []);

  const sendMessage = () => {
    try {
      const payload = JSON.stringify({
        text: message,
        email,
      });
      stompClient.send('/app/chatgpt', {}, payload);

      // console.log('Message sent successfully');
    } catch (error) {
      console.error('Error sending message:', error);
    }
  };

  const onError = (err) => {
    console.log(err);
  };

  return (
    <div>
      {messages.map((message, index) => (
        <div key={index} className={message.role}>
          {message.content}
        </div>
      ))}
      <div>
        <input type="text" onChange={(e) => setMessage(e.target.value)} />
        <button onClick={sendMessage}>Send</button>
      </div>
    </div>
  );
}

export default ChatGptPage;
