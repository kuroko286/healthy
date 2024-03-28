import { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { selectUserData } from '../../redux/selectors';
import SockJS from 'sockjs-client';
import { over } from 'stompjs';

let stompClient = null;
function Main() {
  const {
    user: { email },
  } = useSelector(selectUserData);
  const [messages, setMessages] = useState([]);
  const [message, setMessage] = useState('');
  useEffect(() => {
    const fetchMessages = async () => {
      try {
        const response = await fetch('http://localhost:8080/v1/messages', {
          method: 'GET',
        });
        const data = await response.json();
        setMessages(data);
      } catch (error) {
        console.error('Error fetching messages:', error);
      }
    };

    fetchMessages();
  }, []);

  const sendMessage = () => {
    try {
      const payload = JSON.stringify({
        message,
        email,
      });
      stompClient.send('/app/chatgpt', {}, payload);

      console.log('Message sent successfully');
    } catch (error) {
      console.error('Error sending message:', error);
    }
  };

  const connect = () => {
    let Sock = new SockJS('https://localhost:8080/chatgpt');
    stompClient = over(Sock);
    stompClient.connect({}, onConnected, onError);
  };
  const onConnected = () => {
    stompClient.subscribe('/user/' + email + '/private', onMessageReceived);
  };

  const onError = (err) => {
    console.log(err);
  };
  const onMessageReceived = (payload) => {
    console.log(payload);
    const payloadData = JSON.parse(payload.body);
    setMessages([...messages, payloadData]);
  };

  useEffect(() => {
    connect();
  }, []);

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

export default Main;
